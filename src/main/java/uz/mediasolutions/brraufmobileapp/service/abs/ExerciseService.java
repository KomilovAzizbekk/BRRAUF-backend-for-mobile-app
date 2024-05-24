package uz.mediasolutions.brraufmobileapp.service.abs;

import org.springframework.data.domain.Page;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseStudentDTO;

public interface ExerciseService {

    ApiResult<Page<ExerciseDTO>> get(int page, int size, Long exerciseTypeId);

    ApiResult<ExerciseDTO> getById(Long id);

    ApiResult<?> add(ExerciseReqDTO dto);

    ApiResult<Page<ExerciseStudentDTO>> getByStudentId(int page, int size, Long studentId);

}
