package uz.mediasolutions.brraufmobileapp.mapper;

import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.entity.Scores;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseStudentDTO;
import uz.mediasolutions.brraufmobileapp.payload.ScoreDTO;

import java.util.List;

public interface ExerciseMapper {

    Exercise toEntity(ExerciseReqDTO dto);

    ExerciseDTO toDTO(Exercise exercise);

    ExerciseStudentDTO toStudentDTO(Exercise exercise);

    ScoreDTO scoreDTO(Scores scores);

    List<ScoreDTO> scoreDTO(List<Scores> scores);

}
