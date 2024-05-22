package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseTypeDTO;

public interface ExerciseTypeService {

    ApiResult<Page<ExerciseTypeDTO>> get(int page, int size);

    ApiResult<ExerciseTypeDTO> getById(Long id);

    ApiResult<?> add(ExerciseTypeDTO dto);

    ApiResult<?> edit(Long id, ExerciseTypeDTO dto);

    ApiResult<?> delete(Long id);

}
