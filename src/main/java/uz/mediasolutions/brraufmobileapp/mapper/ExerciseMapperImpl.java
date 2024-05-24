package uz.mediasolutions.brraufmobileapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.brraufmobileapp.entity.*;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.payload.*;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseTypeRepository;
import uz.mediasolutions.brraufmobileapp.repository.ScoreRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseMapperImpl implements ExerciseMapper {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseTypeMapper exerciseTypeMapper;
    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;

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

    @Override
    public ExerciseStudentDTO toStudentDTO(Exercise exercise) {
        if (exercise == null) {
            return null;
        }

        User user = CommonUtils.getUserFromSecurityContext();

        Student student = studentRepository.findByUser(user);

        List<Scores> scores = scoreRepository.findAllByStudentIdAndExerciseId(student.getId(), exercise.getId());

        return ExerciseStudentDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .exerciseType(exerciseTypeMapper.toDto(exercise.getExerciseType()))
                .createdTime(exercise.getCreatedAt().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .scores(scoreDTO(scores))
                .build();

    }


    @Override
    public ScoreDTO scoreDTO(Scores scores) {
        if (scores == null) {
            return null;
        }

        return ScoreDTO.builder()
                .score(scores.getScore())
                .criteriaId(scores.getCriteria().getId())
                .build();
    }


    @Override
    public List<ScoreDTO> scoreDTO(List<Scores> scores) {
        if (scores == null) {
            return null;
        }

        List<ScoreDTO> scoresDTO = new ArrayList<>();
        scores.forEach(score -> scoresDTO.add(scoreDTO(score)));
        return scoresDTO;
    }
}
