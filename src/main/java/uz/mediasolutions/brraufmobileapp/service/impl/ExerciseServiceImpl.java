package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseRepository;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseService;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final TrainingCenterRepository trainingCenterRepository;

    @Override
    public ApiResult<Page<ExerciseDTO>> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        User user = CommonUtils.getUserFromRequest();
        Page<ExerciseDTO> dtoPage;
        if (user.getRole().getName().equals(RoleName.ROLE_SUPER_ADMIN)) {
            Page<Exercise> exercises = exerciseRepository.findAll(pageable);
            dtoPage = exercises.map(exerciseMapper::toDTO);
        } else {
            TrainingCenter trainingCenter = trainingCenterRepository.findByUser(user);
            Page<Exercise> exercises = exerciseRepository.findByTrainingCenter(trainingCenter, pageable);
            dtoPage = exercises.map(exerciseMapper::toDTO);
        }
        return ApiResult.success(dtoPage);
    }

    @Override
    public ApiResult<ExerciseDTO> getById(Long id) {
        User user = CommonUtils.getUserFromRequest();
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise not found", HttpStatus.BAD_REQUEST));
        if (user.getRole().getName().equals(RoleName.ROLE_SUPER_ADMIN)) {
            ExerciseDTO dto = exerciseMapper.toDTO(exercise);
            return ApiResult.success(dto);
        } else {
            TrainingCenter trainingCenter = trainingCenterRepository.findByUser(user);
           if (exercise.getTrainingCenter().getId().equals(trainingCenter.getId())) {
               ExerciseDTO dto = exerciseMapper.toDTO(exercise);
               return ApiResult.success(dto);
           } else {
               throw RestException.restThrow("Exercise is not belonged to your training center", HttpStatus.BAD_REQUEST);
           }
        }
    }

    @Override
    public ApiResult<?> add(ExerciseReqDTO dto) {
        Exercise exercise = exerciseMapper.toEntity(dto);
        exerciseRepository.save(exercise);
        return ApiResult.success("Successfully saved");
    }
}
