package uz.mediasolutions.brraufmobileapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.brraufmobileapp.entity.*;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.Student2DTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseTypeRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ExerciseMapperImpl implements ExerciseMapper{

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseTypeMapper exerciseTypeMapper;

    @Override
    public Exercise toEntity(ExerciseReqDTO dto) {
        if (dto == null) {
            return null;
        }

        ExerciseType exerciseType = exerciseTypeRepository.findById(dto.getExerciseTypeId()).orElseThrow(
                () -> RestException.restThrow("Exercise type not found", HttpStatus.BAD_REQUEST));

        return Exercise.builder()
                .exerciseType(exerciseType)
                .name(dto.getName())
                .build();

    }

    @Override
    public ExerciseDTO toDTO(Exercise exercise) {
        if (exercise == null) {
            return null;
        }

        return ExerciseDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .exerciseType(exerciseTypeMapper.toDto(exercise.getExerciseType()))
                .createdTime(exercise.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .build();
    }
}
