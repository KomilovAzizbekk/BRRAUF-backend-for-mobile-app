package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseService;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final TrainingCenterRepository trainingCenterRepository;
    private final StudentRepository studentRepository;

    @Override
    public ApiResult<Page<ExerciseDTO>> get(int page, int size, Long exerciseTypeId, Long trainingCenterId) {
        Pageable pageable = PageRequest.of(page, size);
        User user = CommonUtils.getUserFromSecurityContext();
        Page<ExerciseDTO> dtoPage;
        Page<Exercise> exercises;
        assert user != null;
        if (user.getRole().getName().equals(RoleName.ROLE_SUPER_ADMIN)) {
            if (exerciseTypeId != null || trainingCenterId != null) {
                exercises = exerciseRepository.findByFilter(exerciseTypeId, trainingCenterId, pageable);
            } else {
                exercises = exerciseRepository.findAll(pageable);
            }
        } else {
            TrainingCenter trainingCenter = trainingCenterRepository.findByUser(user);
            if (exerciseTypeId != null) {
                exercises = exerciseRepository.findByFilter(exerciseTypeId, trainingCenter.getId(), pageable);
            } else {
                exercises = exerciseRepository.findByTrainingCenter(trainingCenter, pageable);
            }
        }
        dtoPage = exercises.map(exerciseMapper::toDTO);
        return ApiResult.success(dtoPage);
    }

    @Override
    public ApiResult<ExerciseDTO> getById(Long id) {
        User user = CommonUtils.getUserFromSecurityContext();
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise not found", HttpStatus.BAD_REQUEST));
        assert user != null;
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
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));

        int count = exerciseRepository.countAllByStudentId(dto.getStudentId());

        student.setSpentTime((student.getSpentTime() * count + dto.getSpentTime()) / (count + 1));
        student.setSequence((student.getSequence() * count + dto.getSequence())/(count + 1));
        student.setError((student.getError() * count + dto.getError())/(count + 1));
        studentRepository.save(student);

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
