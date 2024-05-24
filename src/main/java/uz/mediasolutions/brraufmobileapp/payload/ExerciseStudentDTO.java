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
public class ExerciseStudentDTO {

    private long id;

    private String name;

    private ExerciseTypeDTO exerciseType;

    private String createdTime;

    private List<ScoreDTO> scores;

}
