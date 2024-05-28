package uz.mediasolutions.brraufmobileapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    private long id;

    private String fullName;

    private String phoneNumber;

    private String username;

    private String password;

    private long trainingCenterId;

    private String trainingCenterName;

}
