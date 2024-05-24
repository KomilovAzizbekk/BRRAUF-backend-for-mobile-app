package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;

public interface ExerciseResultService {

    ApiResult<Page<ExerciseResultDTO>> get(int page, int size);

    ApiResult<ExerciseResultDTO> getById(Long id);

    ApiResult<?> add(ExerciseResultReqDTO dto);
}
