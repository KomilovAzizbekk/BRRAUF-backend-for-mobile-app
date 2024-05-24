package uz.mediasolutions.brraufmobileapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseResultDTO {

    private long id;

    private Student2DTO student;

    private ExerciseDTO exercise;

    private List<ScoreDTO> scores;

}
