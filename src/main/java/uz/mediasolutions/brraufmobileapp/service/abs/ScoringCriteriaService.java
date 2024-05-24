package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ScoringCriteriaDTO;

public interface ScoringCriteriaService {

    ApiResult<Page<ScoringCriteriaDTO>> get(int page, int size);

    ApiResult<ScoringCriteriaDTO> getById(Long id);

    ApiResult<?> add(String name);

    ApiResult<?> edit(Long id, String name);

    ApiResult<?> delete(Long id);

}
