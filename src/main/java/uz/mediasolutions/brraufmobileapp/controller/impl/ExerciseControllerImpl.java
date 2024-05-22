package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.ExerciseController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseService;

@RestController
@RequiredArgsConstructor
public class ExerciseControllerImpl implements ExerciseController {

    private final ExerciseService exerciseService;

    @Override
    public ApiResult<Page<ExerciseDTO>> get(int page, int size) {
        return exerciseService.get(page, size);
    }

    @Override
    public ApiResult<ExerciseDTO> getById(Long id) {
        return exerciseService.getById(id);
    }

    @Override
    public ApiResult<?> add(ExerciseReqDTO dto) {
        return exerciseService.add(dto);
    }
}
