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

    private long spentTime;

    private int sequence;

    private int error;

    private String username;

    private String password;

}
