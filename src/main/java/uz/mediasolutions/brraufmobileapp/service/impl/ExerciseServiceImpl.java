package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseService;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public ApiResult<Page<ExerciseDTO>> get(int page, int size, Long exerciseTypeId) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Exercise> exercises;

        if (exerciseTypeId != null) {
            exercises = exerciseRepository.findByFilter(exerciseTypeId, pageable);
        } else {
            exercises = exerciseRepository.findAll(pageable);
        }
        Page<ExerciseDTO> dtoPage = exercises.map(exerciseMapper::toDTO);
        return ApiResult.success(dtoPage);
    }

    @Override
    public ApiResult<ExerciseDTO> getById(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise not found", HttpStatus.BAD_REQUEST));

        ExerciseDTO dto = exerciseMapper.toDTO(exercise);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(ExerciseReqDTO dto) {
        Exercise exercise = exerciseMapper.toEntity(dto);
        exerciseRepository.save(exercise);

        return ApiResult.success("Successfully saved");
    }

    @Override
    public ApiResult<Page<ExerciseDTO>> getByStudentId(int page, int size, Long studentId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercises = exerciseRepository.findAllByStudentId(studentId, pageable);
        Page<ExerciseDTO> dtoPage = exercises.map(exerciseMapper::toDTO);
        return ApiResult.success(dtoPage);
    }
}
