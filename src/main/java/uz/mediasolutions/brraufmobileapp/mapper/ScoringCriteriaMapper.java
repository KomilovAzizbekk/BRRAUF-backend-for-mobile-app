package uz.mediasolutions.brraufmobileapp.mapper;

import org.mapstruct.Mapper;
import uz.mediasolutions.brraufmobileapp.entity.ScoringCriteria;
import uz.mediasolutions.brraufmobileapp.payload.ScoringCriteriaDTO;

@Mapper(componentModel = "spring")
public interface ScoringCriteriaMapper {

    ScoringCriteriaDTO toDTO(ScoringCriteria scoringCriteria);

}
