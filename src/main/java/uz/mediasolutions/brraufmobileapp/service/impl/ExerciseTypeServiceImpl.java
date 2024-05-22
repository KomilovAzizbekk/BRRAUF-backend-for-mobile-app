package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseType;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ExerciseTypeMapper;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseTypeDTO;
import uz.mediasolutions.brraufmobileapp.repository.ExerciseTypeRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ExerciseTypeService;

@Service
@RequiredArgsConstructor
public class ExerciseTypeServiceImpl implements ExerciseTypeService {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final ExerciseTypeMapper exerciseTypeMapper;

    @Override
    public ApiResult<Page<ExerciseTypeDTO>> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExerciseType> exerciseTypes = exerciseTypeRepository.findAll(pageable);
        Page<ExerciseTypeDTO> dtoPage = exerciseTypes.map(exerciseTypeMapper::toDto);
        return ApiResult.success(dtoPage);
    }

    @Override
    public ApiResult<ExerciseTypeDTO> getById(Long id) {
        ExerciseType exerciseType = exerciseTypeRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise type not found", HttpStatus.BAD_REQUEST));
        return ApiResult.success(exerciseTypeMapper.toDto(exerciseType));
    }

    @Override
    public ApiResult<?> add(ExerciseTypeDTO dto) {
        ExerciseType exerciseType = exerciseTypeMapper.toEntity(dto);
        exerciseTypeRepository.save(exerciseType);
        return ApiResult.success("Successfully saved");
    }

    @Override
    public ApiResult<?> edit(Long id, ExerciseTypeDTO dto) {
        ExerciseType exerciseType = exerciseTypeRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise type not found", HttpStatus.BAD_REQUEST));
        exerciseType.setId(id);
        exerciseType.setName(dto.getName());
        exerciseTypeRepository.save(exerciseType);
        return ApiResult.success("Successfully edited");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        ExerciseType exerciseType = exerciseTypeRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Exercise type not found", HttpStatus.BAD_REQUEST));
        try {
            exerciseTypeRepository.delete(exerciseType);
            return ApiResult.success("Successfully deleted");
        } catch (Exception e) {
            throw new RestException("Error while deleting exercise type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
