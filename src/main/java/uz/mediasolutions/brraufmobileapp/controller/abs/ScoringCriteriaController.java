package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ScoringCriteriaDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(ScoringCriteriaController.SCORING_CRITERIA)
public interface ScoringCriteriaController {

    String SCORING_CRITERIA = Rest.BASE_PATH + "scoring-criteria/";
    String GET = "get";
    String GET_BY_ID = "get-by/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STUDENT')")
    ApiResult<Page<ScoringCriteriaDTO>> get(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                            @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STUDENT')")
    ApiResult<ScoringCriteriaDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> add(@RequestParam String name);

    @PutMapping(EDIT)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> edit(@PathVariable Long id,
                      @RequestParam String name);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> delete(@PathVariable Long id);
}
