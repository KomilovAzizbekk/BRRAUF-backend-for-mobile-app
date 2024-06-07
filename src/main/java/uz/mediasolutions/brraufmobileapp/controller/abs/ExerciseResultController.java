package uz.mediasolutions.brraufmobileapp.controller.abs;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.ScoringCriteriaDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(ExerciseResultController.EXERCISE_RESULT)
public interface ExerciseResultController {

    String EXERCISE_RESULT = Rest.BASE_PATH + "exercise-result/";
    String GET = "get";
    String GET_BY_ID = "get-by/{id}";
    String ADD = "add";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STUDENT')")
    ApiResult<Page<ExerciseResultDTO>> get(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                           @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                           @RequestParam(required = false) Long trainingCenterId);

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN', 'ROLE_STUDENT')")
    ApiResult<ExerciseResultDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    ApiResult<?> add(@RequestBody ExerciseResultReqDTO dto);
}
