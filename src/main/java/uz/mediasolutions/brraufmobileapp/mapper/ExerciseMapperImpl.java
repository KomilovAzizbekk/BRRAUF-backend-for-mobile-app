package uz.mediasolutions.brraufmobileapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseType;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.Student2DTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseTypeRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ExerciseMapperImpl implements ExerciseMapper{

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final StudentRepository studentRepository;
    private final ExerciseTypeMapper exerciseTypeMapper;
    private final TrainingCenterRepository trainingCenterRepository;

    @Override
    public Exercise toEntity(ExerciseReqDTO dto) {
        if (dto == null) {
            return null;
        }

        ExerciseType exerciseType = exerciseTypeRepository.findById(dto.getExerciseTypeId()).orElseThrow(
                () -> RestException.restThrow("Exercise type not found", HttpStatus.BAD_REQUEST));

        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));

        TrainingCenter trainingCenter = trainingCenterRepository.findByStudentsContains(student);

        return Exercise.builder()
                .exerciseType(exerciseType)
                .spentTime(dto.getSpentTime())
                .sequence(dto.getSequence())
                .error(dto.getError())
                .student(student)
                .trainingCenter(trainingCenter)
                .build();

    }

    @Override
    public ExerciseDTO toDTO(Exercise exercise) {
        if (exercise == null) {
            return null;
        }

        Student2DTO student2DTO = Student2DTO.builder()
                .id(exercise.getStudent().getId())
                .fullName(exercise.getStudent().getFullName())
                .phoneNumber(exercise.getStudent().getPhoneNumber())
                .build();

        return ExerciseDTO.builder()
                .id(exercise.getId())
                .type(exerciseTypeMapper.toDto(exercise.getExerciseType()))
                .student(student2DTO)
                .sequence(exercise.getSequence())
                .spentTime(exercise.getSpentTime())
                .error(exercise.getError())
                .createdTime(exercise.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .build();
    }
}
