package uz.mediasolutions.brraufmobileapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExerciseReqDTO {

    private long studentId;

    private long exerciseTypeId;

    private long spentTime;

    private int sequence;

    private int error;

}
