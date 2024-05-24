package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.ScoringCriteriaController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ScoringCriteriaDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.ScoringCriteriaService;

@RestController
@RequiredArgsConstructor
public class ScoringCriteriaControllerImpl implements ScoringCriteriaController {

    private final ScoringCriteriaService scoringCriteriaService;

    @Override
    public ApiResult<Page<ScoringCriteriaDTO>> get(int page, int size) {
        return scoringCriteriaService.get(page, size);
    }

    @Override
    public ApiResult<ScoringCriteriaDTO> getById(Long id) {
        return scoringCriteriaService.getById(id);
    }

    @Override
    public ApiResult<?> add(String name) {
        return scoringCriteriaService.add(name);
    }

    @Override
    public ApiResult<?> edit(Long id, String name) {
        return scoringCriteriaService.edit(id, name);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return scoringCriteriaService.delete(id);
    }
}
