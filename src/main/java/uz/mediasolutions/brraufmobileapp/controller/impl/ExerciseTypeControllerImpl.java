package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.ExerciseTypeController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseTypeDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseTypeService;

@RestController
@RequiredArgsConstructor
public class ExerciseTypeControllerImpl implements ExerciseTypeController {

    private final ExerciseTypeService exerciseTypeService;

    @Override
    public ApiResult<Page<ExerciseTypeDTO>> get(int page, int size) {
        return exerciseTypeService.get(page, size);
    }

    @Override
    public ApiResult<ExerciseTypeDTO> getById(Long id) {
        return exerciseTypeService.getById(id);
    }

    @Override
    public ApiResult<?> add(ExerciseTypeDTO dto) {
        return exerciseTypeService.add(dto);
    }

    @Override
    public ApiResult<?> edit(Long id, ExerciseTypeDTO dto) {
        return exerciseTypeService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return exerciseTypeService.delete(id);
    }
}
