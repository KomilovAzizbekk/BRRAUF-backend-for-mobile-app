package uz.mediasolutions.brraufmobileapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentReqDTO {

    private String fullName;

    private String phoneNumber;

    private String username;

    private String password;

    private Long trainingCenterId;

}
