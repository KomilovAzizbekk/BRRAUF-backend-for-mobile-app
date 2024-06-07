package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseResult;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseResultMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseResultRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseResultService;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ExerciseResultServiceImpl implements ExerciseResultService {

    private final ExerciseResultRepository exerciseResultRepository;
    private final ExerciseResultMapper exerciseResultMapper;
    private final TrainingCenterRepository trainingCenterRepository;
    private final StudentRepository studentRepository;

    @Override
    public ApiResult<Page<ExerciseResultDTO>> get(int page, int size, Long trainingCenterId) {
        User user = CommonUtils.getUserFromSecurityContext();
        Pageable pageable = PageRequest.of(page, size);

        Page<ExerciseResult> results;

        assert user != null;
        if (user.getRole().getName().equals(RoleName.ROLE_SUPER_ADMIN)) {
            if (trainingCenterId != null) {
                results = exerciseResultRepository.findAllByTrainingCenterId(trainingCenterId, pageable);
            } else {
                results = exerciseResultRepository.findAll(pageable);
            }
        } else if (user.getRole().getName().equals(RoleName.ROLE_ADMIN)) {
            TrainingCenter trainingCenter = trainingCenterRepository.findByUser(user);
            results = exerciseResultRepository.findAllByTrainingCenterId(trainingCenter.getId(), pageable);
        } else {
            Student student = studentRepository.findByUser(user);
            results = exerciseResultRepository.findAllByStudentId(student.getId(), pageable);
        }

        Page<ExerciseResultDTO> map = results.map(exerciseResultMapper::toDTO);
        return ApiResult.success(map);
    }

    @Override
    public ApiResult<ExerciseResultDTO> getById(Long id) {
        ExerciseResult result = exerciseResultRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise result not found", HttpStatus.BAD_REQUEST));
        ExerciseResultDTO dto = exerciseResultMapper.toDTO(result);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(ExerciseResultReqDTO dto) {
        ExerciseResult entity = exerciseResultMapper.toEntity(dto);
        exerciseResultRepository.save(entity);
        return ApiResult.success("Successfully saved");
    }
}
