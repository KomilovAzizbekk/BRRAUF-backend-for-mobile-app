package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.TrainingCenterMapper;
import uz.mediasolutions.brraufmobileapp.payload.TrainingCenterDTO;
import uz.mediasolutions.brraufmobileapp.repository.TrainingCenterRepository;
import uz.mediasolutions.brraufmobileapp.repository.UserRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.TrainingCenterService;

@Service
@RequiredArgsConstructor
public class TrainingCenterServiceImpl implements TrainingCenterService {

    private final UserRepository userRepository;
    private final TrainingCenterRepository trainingCenterRepository;
    private final TrainingCenterMapper trainingCenterMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResult<Page<TrainingCenterDTO>> get(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingCenterDTO> trainingCenterDTOPage;
        Page<TrainingCenter> trainingCenters;
        if (search == null || search.isEmpty()) {
            trainingCenters = trainingCenterRepository.findAllByOrderByNameAsc(pageable);
        } else {
            trainingCenters = trainingCenterRepository.findAllByNameContainsIgnoreCaseOrderByName(search, pageable);
        }
        trainingCenterDTOPage = trainingCenters.map(trainingCenterMapper::toDTO);
        return ApiResult.success(trainingCenterDTOPage);
    }

    @Override
    public ApiResult<TrainingCenterDTO> getById(Long id) {
        TrainingCenter trainingCenter = trainingCenterRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Training center not found", HttpStatus.BAD_REQUEST));
        TrainingCenterDTO dto = trainingCenterMapper.toDTO(trainingCenter);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(TrainingCenterDTO dto) {
        TrainingCenter trainingCenter = trainingCenterMapper.toEntity(dto);
        trainingCenterRepository.save(trainingCenter);
        return ApiResult.success("Successfully saved");
    }

    @Override
    public ApiResult<?> edit(Long id, TrainingCenterDTO dto) {
        TrainingCenter trainingCenter = trainingCenterRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Training center not found", HttpStatus.BAD_REQUEST));
        trainingCenter.setName(dto.getName());
        trainingCenter.setInfo(dto.getInfo());

        User user = userRepository.findByUsername(dto.getUsername());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);

        trainingCenter.setUser(saved);
        trainingCenterRepository.save(trainingCenter);
        return ApiResult.success("Successfully edited");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        TrainingCenter trainingCenter = trainingCenterRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Training center not found", HttpStatus.BAD_REQUEST));
        try {
            trainingCenterRepository.delete(trainingCenter);
            userRepository.delete(trainingCenter.getUser());
            return ApiResult.success("Successfully deleted");
        } catch (Exception e) {
            throw RestException.restThrow("Error deleting training center", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
