package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.StudentMapper;
import uz.mediasolutions.brraufmobileapp.payload.StudentDTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentReqDTO;
import uz.mediasolutions.brraufmobileapp.repository.RoleRepository;
import uz.mediasolutions.brraufmobileapp.repository.StudentRepository;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;
import uz.mediasolutions.brraufmobileapp.repository.UserRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.StudentService;
import uz.mediasolutions.brraufmobileapp.utills.CommonUtils;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TrainingCenterRepository trainingCenterRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResult<Page<StudentDTO>> get(int page, int size, String search, Long trainingCenterId) {
        Pageable pageable = PageRequest.of(page, size);
        User user = CommonUtils.getUserFromSecurityContext();

        Page<Student> students = null;
        Page<StudentDTO> studentDTOs;

        assert user != null;
        if (user.getRole().getName().equals(RoleName.ROLE_SUPER_ADMIN)) {
            if (search != null || trainingCenterId != null) {
                students = studentRepository.findBySearchAndFilter(search, trainingCenterId, pageable);
            } else {
                students = studentRepository.findAllByOrderByFullNameAsc(pageable);
            }
        } else if (user.getRole().getName().equals(RoleName.ROLE_ADMIN)){
            TrainingCenter trainingCenter = trainingCenterRepository.findByUser(user);
            if (search != null) {
                students = studentRepository.findBySearchAndFilter(search, trainingCenter.getId(), pageable);
            } else {
                students = studentRepository.findAllByTrainingCenterIdOrderByFullNameAsc(trainingCenter.getId(), pageable);
            }
        }
        assert students != null;
        studentDTOs = students.map(studentMapper::toDTO);
        return ApiResult.success(studentDTOs);
    }

    @Override
    public ApiResult<StudentDTO> getById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));
        StudentDTO dto = studentMapper.toDTO(student);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(StudentReqDTO dto) {

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(roleRepository.findByName(RoleName.ROLE_STUDENT))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .credentialsNonExpired(true)
                .build();

        User saved = userRepository.save(user);

        Student student = studentMapper.toEntity(dto);
        student.setUser(saved);
        studentRepository.save(student);
        return ApiResult.success("Successfully added");
    }

    @Override
    public ApiResult<?> edit(Long id, StudentReqDTO dto) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));

        TrainingCenter trainingCenter = trainingCenterRepository.findById(dto.getTrainingCenterId()).orElseThrow(
                () -> RestException.restThrow("TrainingCenter not found", HttpStatus.BAD_REQUEST));

        student.setFullName(dto.getFullName());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setTrainingCenter(trainingCenter);

        User user = student.getUser();
        user.setUsername(dto.getUsername());
        if (dto.getPassword() != null)
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);

        student.setUser(saved);
        studentRepository.save(student);
        return ApiResult.success("Successfully edited");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Student not found", HttpStatus.BAD_REQUEST));
        try {
            studentRepository.delete(student);
            userRepository.delete(student.getUser());
            return ApiResult.success("Successfully deleted");
        } catch (Exception e) {
            throw RestException.restThrow("Error deleting student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
