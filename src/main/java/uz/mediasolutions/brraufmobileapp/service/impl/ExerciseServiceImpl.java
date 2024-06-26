package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseType;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseStudentDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseRepository;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseTypeRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseService;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;
    private final ExerciseTypeRepository exerciseTypeRepository;

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
    public ApiResult<Page<ExerciseStudentDTO>> getByStudentId(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Exercise> exercises = exerciseRepository.findAll(pageable);
        Page<ExerciseStudentDTO> dtoPage = exercises.map(exerciseMapper::toStudentDTO);
        return ApiResult.success(dtoPage);
    }

    @Override
    public ApiResult<?> edit(Long id, ExerciseReqDTO dto) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise not found", HttpStatus.BAD_REQUEST));

        ExerciseType exerciseType = exerciseTypeRepository.findById(dto.getExerciseTypeId()).orElseThrow(
                () -> RestException.restThrow("Exercise type not found", HttpStatus.BAD_REQUEST));

        exercise.setName(dto.getName());
        exercise.setExerciseType(exerciseType);
        exerciseRepository.save(exercise);
        return ApiResult.success("Successfully edited");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise not found", HttpStatus.BAD_REQUEST));
        try {
            exerciseRepository.delete(exercise);
            return ApiResult.success("Successfully deleted");
        } catch (Exception e) {
            throw RestException.restThrow("Error deleting exercise", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
