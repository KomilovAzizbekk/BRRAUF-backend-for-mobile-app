package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.StudentDTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentReqDTO;

public interface StudentService {

    ApiResult<Page<StudentDTO>> get(int page, int size, String search, Long trainingCenterId);

    ApiResult<StudentDTO> getById(Long id);

    ApiResult<?> add(StudentReqDTO dto);

    ApiResult<?> edit(Long id, StudentReqDTO dto);

    ApiResult<?> delete(Long id);

}
