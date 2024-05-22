package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.TrainingCenterDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(TrainingCenterController.TRAINING_CENTER)
public interface TrainingCenterController {

    String TRAINING_CENTER = Rest.BASE_PATH + "training-center/";
    String GET = "get";
    String GET_BY_ID = "get-by/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<Page<TrainingCenterDTO>> get(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                           @RequestParam(required = false) String search);

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<TrainingCenterDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> add(@RequestBody TrainingCenterDTO dto);

    @PutMapping(EDIT)
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    ApiResult<?> edit(@PathVariable Long id,
                      @RequestBody TrainingCenterDTO dto);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> delete(@PathVariable Long id);
}
