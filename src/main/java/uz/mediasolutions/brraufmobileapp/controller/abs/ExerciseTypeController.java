package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseTypeDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(ExerciseTypeController.EXERCISE_TYPE)
public interface ExerciseTypeController {

    String EXERCISE_TYPE = Rest.BASE_PATH + "exercise-type/";
    String GET = "get";
    String GET_BY_ID = "get-by/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<Page<ExerciseTypeDTO>> get(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                         @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size);

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<ExerciseTypeDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> add(@RequestBody ExerciseTypeDTO dto);

    @PutMapping(EDIT)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> edit(@PathVariable Long id,
                      @RequestBody ExerciseTypeDTO dto);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    ApiResult<?> delete(@PathVariable Long id);
}
