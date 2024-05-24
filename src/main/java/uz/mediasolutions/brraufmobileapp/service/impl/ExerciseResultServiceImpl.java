package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseResult;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseResultMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseResultRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseResultService;

@Service
@RequiredArgsConstructor
public class ExerciseResultServiceImpl implements ExerciseResultService {

    private final ExerciseResultRepository exerciseResultRepository;
    private final ExerciseResultMapper exerciseResultMapper;

    @Override
    public ApiResult<Page<ExerciseResultDTO>> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExerciseResult> exerciseResults = exerciseResultRepository.findAll(pageable);
        Page<ExerciseResultDTO> map = exerciseResults.map(exerciseResultMapper::toDTO);
        return ApiResult.success(map);
    }

    @Override
    public ApiResult<ExerciseResultDTO> getById(Long id) {
        ExerciseResult result = exerciseResultRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise result not found", HttpStatus.BAD_REQUEST));
        ExerciseResultDTO dto = exerciseResultMapper.toDTO(result);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(ExerciseResultReqDTO dto) {
        ExerciseResult entity = exerciseResultMapper.toEntity(dto);
        exerciseResultRepository.save(entity);
        return ApiResult.success("Successfully saved");
    }
}
