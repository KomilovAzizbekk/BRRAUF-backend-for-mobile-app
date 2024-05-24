package uz.mediasolutions.brraufmobileapp.controller.abs;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentDTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentReqDTO;
import uz.mediasolutions.brraufmobileapp.utills.constants.Rest;

@RequestMapping(StudentController.STUDENT)
public interface StudentController {

    String STUDENT = Rest.BASE_PATH + "student/";
    String GET = "get";
    String GET_BY_ID = "get-by/{id}";
    String ADD = "add";
    String EDIT = "edit/{id}";
    String DELETE = "delete/{id}";

    @GetMapping(GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<Page<StudentDTO>> get(@RequestParam(defaultValue = Rest.DEFAULT_PAGE_NUMBER) int page,
                                    @RequestParam(defaultValue = Rest.DEFAULT_PAGE_SIZE) int size,
                                    @RequestParam(required = false) String search,
                                    @RequestParam(required = false) Long trainingCenterId);

    @GetMapping(GET_BY_ID)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<StudentDTO> getById(@PathVariable Long id);

    @PostMapping(ADD)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<?> add(@RequestBody StudentReqDTO dto);

    @PutMapping(EDIT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<?> edit(@PathVariable Long id,
                      @RequestBody StudentReqDTO dto);

    @DeleteMapping(DELETE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    ApiResult<?> delete(@PathVariable Long id);
}
