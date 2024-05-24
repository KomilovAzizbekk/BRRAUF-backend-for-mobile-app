package uz.mediasolutions.brraufmobileapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.mediasolutions.brraufmobileapp.entity.*;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.ScoreDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseRepository;
import uz.mediasolutions.brraufmobileapp.repository.ScoreRepository;
import uz.mediasolutions.brraufmobileapp.repository.ScoringCriteriaRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExerciseResultMapperImpl implements ExerciseResultMapper {

    private final ExerciseRepository exerciseRepository;
    private final StudentRepository studentRepository;
    private final ScoringCriteriaRepository scoringCriteriaRepository;
    private final ScoreRepository scoreRepository;
    private final StudentMapper studentMapper;
    private final ExerciseMapper exerciseMapper;

    @Override
    public ExerciseResult toEntity(ExerciseResultReqDTO dto) {
        if (dto == null) {
            return null;
        }

        Exercise exercise = exerciseRepository.findById(dto.getExerciseId()).orElseThrow(
                () -> RestException.restThrow("Exercise not found", HttpStatus.BAD_REQUEST));

        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));

        return ExerciseResult.builder()
                .exercise(exercise)
                .student(student)
                .scores(toScores(dto.getScores()))
                .build();
    }

    @Override
    public ExerciseResultDTO toDTO(ExerciseResult result) {
        if (result == null) {
            return null;
        }

        return ExerciseResultDTO.builder()
                .id(result.getId())
                .student(studentMapper.toStudent2DTO(result.getStudent()))
                .exercise(exerciseMapper.toDTO(result.getExercise()))
                .scores(exerciseMapper.scoreDTO(result.getScores()))
                .build();
    }

    private Scores toScores(ScoreDTO dto) {
        if (dto == null) {
            return null;
        }

        ScoringCriteria criteria = scoringCriteriaRepository.findById(dto.getCriteriaId()).orElseThrow(
                () -> RestException.restThrow("Criteria not found", HttpStatus.BAD_REQUEST));

        Scores scores = Scores.builder()
                .score(dto.getScore())
                .criteria(criteria)
                .build();

        return scoreRepository.save(scores);
    }

    private List<Scores> toScores(List<ScoreDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<Scores> scores = new ArrayList<>();
        for (ScoreDTO dto : dtos) {
            scores.add(toScores(dto));
        }
        return scores;
    }

}
