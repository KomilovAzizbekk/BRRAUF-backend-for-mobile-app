package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.TrainingCenterDTO;

public interface TrainingCenterService {

    ApiResult<Page<TrainingCenterDTO>> get(int page, int size, String search);

    ApiResult<TrainingCenterDTO> getById(Long id);

    ApiResult<?> add(TrainingCenterDTO dto);

    ApiResult<?> edit(Long id, TrainingCenterDTO dto);

    ApiResult<?> delete(Long id);
}
