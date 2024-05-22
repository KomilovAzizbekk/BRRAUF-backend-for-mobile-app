package uz.mediasolutions.brraufmobileapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseDTO {

    private long id;

    private ExerciseTypeDTO type;

    private Student2DTO student;

    private long spentTime;

    private int sequence;

    private int error;

    private String createdTime;

}
