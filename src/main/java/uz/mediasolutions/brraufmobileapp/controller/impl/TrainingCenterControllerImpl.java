package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.TrainingCenterController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.TrainingCenterDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.TrainingCenterService;

@RestController
@RequiredArgsConstructor
public class TrainingCenterControllerImpl implements TrainingCenterController {

    private final TrainingCenterService trainingCenterService;

    @Override
    public ApiResult<Page<TrainingCenterDTO>> get(int page, int size, String search) {
        return trainingCenterService.get(page, size, search);
    }

    @Override
    public ApiResult<TrainingCenterDTO> getById(Long id) {
        return trainingCenterService.getById(id);
    }

    @Override
    public ApiResult<?> add(TrainingCenterDTO dto) {
        return trainingCenterService.add(dto);
    }

    @Override
    public ApiResult<?> edit(Long id, TrainingCenterDTO dto) {
        return trainingCenterService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return trainingCenterService.delete(id);
    }
}
