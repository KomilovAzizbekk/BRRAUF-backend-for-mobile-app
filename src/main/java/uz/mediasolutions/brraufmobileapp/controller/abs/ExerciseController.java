package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseTypeDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(ExerciseController.EXERCISE)
public interface ExerciseController {

    String EXERCISE = Rest.BASE_PATH + "exercise/";
    String GET = "get";
    String GET_BY_ID = "get-by/{id}";
    String ADD = "add";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<Page<ExerciseDTO>> get(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                     @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                     @RequestParam(required = false) Long exerciseTypeId,
                                     @RequestParam(required = false) Long trainingCenterId);

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<ExerciseDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    ApiResult<?> add(@RequestBody ExerciseReqDTO dto);
}
