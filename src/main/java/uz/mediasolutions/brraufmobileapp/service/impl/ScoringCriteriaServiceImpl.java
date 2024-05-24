package uz.mediasolutions.brraufmobileapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.mediasolutions.brraufmobileapp.entity.ScoringCriteria;
import uz.mediasolutions.brraufmobileapp.exceptions.RestException;
import uz.mediasolutions.brraufmobileapp.manual.ApiResult;
import uz.mediasolutions.brraufmobileapp.mapper.ScoringCriteriaMapper;
import uz.mediasolutions.brraufmobileapp.payload.ScoringCriteriaDTO;
import uz.mediasolutions.brraufmobileapp.repository.ScoringCriteriaRepository;
import uz.mediasolutions.brraufmobileapp.service.abs.ScoringCriteriaService;

@Service
@RequiredArgsConstructor
public class ScoringCriteriaServiceImpl implements ScoringCriteriaService {

    private final ScoringCriteriaRepository scoringCriteriaRepository;
    private final ScoringCriteriaMapper scoringCriteriaMapper;

    @Override
    public ApiResult<Page<ScoringCriteriaDTO>> get(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ScoringCriteria> criteria = scoringCriteriaRepository.findAll(pageable);
        Page<ScoringCriteriaDTO> dtoPage = criteria.map(scoringCriteriaMapper::toDTO);
        return ApiResult.success(dtoPage);
    }

    @Override
    public ApiResult<ScoringCriteriaDTO> getById(Long id) {
        ScoringCriteria criteria = scoringCriteriaRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Scoring criteria not found", HttpStatus.BAD_REQUEST));
        ScoringCriteriaDTO dto = scoringCriteriaMapper.toDTO(criteria);
        return ApiResult.success(dto);
    }

    @Override
    public ApiResult<?> add(String name) {
        ScoringCriteria scoringCriteria = ScoringCriteria.builder()
                .name(name)
                .build();
        scoringCriteriaRepository.save(scoringCriteria);
        return ApiResult.success("Successfully saved");
    }

    @Override
    public ApiResult<?> edit(Long id, String name) {
        ScoringCriteria criteria = scoringCriteriaRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Scoring criteria not found", HttpStatus.BAD_REQUEST));
        criteria.setName(name);
        scoringCriteriaRepository.save(criteria);
        return ApiResult.success("Successfully edited");
    }

    @Override
    public ApiResult<?> delete(Long id) {
        ScoringCriteria criteria = scoringCriteriaRepository.findById(id).orElseThrow(
                () -> RestException.restThrow("Scoring criteria not found", HttpStatus.BAD_REQUEST));
        try {
            scoringCriteriaRepository.delete(criteria);
            return ApiResult.success("Successfully deleted");
        } catch (Exception e) {
            throw RestException.restThrow("Error deleting scoring criteria", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
