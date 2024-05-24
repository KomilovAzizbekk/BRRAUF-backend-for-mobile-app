package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.ExerciseResultController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseResultService;

@RestController
@RequiredArgsConstructor
public class ExerciseResultControllerImpl implements ExerciseResultController {

    private final ExerciseResultService exerciseResultService;

    @Override
    public ApiResult<Page<ExerciseResultDTO>> get(int page, int size) {
        return exerciseResultService.get(page, size);
    }

    @Override
    public ApiResult<ExerciseResultDTO> getById(Long id) {
        return exerciseResultService.getById(id);
    }

    @Override
    public ApiResult<?> add(ExerciseResultReqDTO dto) {
        return exerciseResultService.add(dto);
    }
}
