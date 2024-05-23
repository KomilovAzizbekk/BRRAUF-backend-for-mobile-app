package uz.mediasolutions.brraufmobileapp.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;
import uz.mediasolutions.brraufmobileapp.controller.abs.StudentController;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.payload.StudentDTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentReqDTO;
import uz.mediasolutions.brraufmobileapp.service.abs.StudentService;

@RestController
@RequiredArgsConstructor
public class StudentControllerImpl implements StudentController {

    private final StudentService studentService;

    @Override
    public ApiResult<Page<StudentDTO>> get(int page, int size, String search, Long trainingCenterId) {
        return studentService.get(page, size, search, trainingCenterId);
    }

    @Override
    public ApiResult<StudentDTO> getById(Long id) {
        return studentService.getById(id);
    }

    @Override
    public ApiResult<?> add(StudentReqDTO dto) {
        return studentService.add(dto);
    }

    @Override
    public ApiResult<?> edit(Long id, StudentReqDTO dto) {
        return studentService.edit(id, dto);
    }

    @Override
    public ApiResult<?> delete(Long id) {
        return studentService.delete(id);
    }
}
