package uz.mediasolutions.brraufmobileapp.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student2DTO {

    private long id;

    private String fullName;

    private String phoneNumber;

}
