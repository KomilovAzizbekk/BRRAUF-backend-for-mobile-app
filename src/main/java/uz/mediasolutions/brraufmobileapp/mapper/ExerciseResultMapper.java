package uz.mediasolutions.brraufmobileapp.mapper;

import uz.mediasolutions.brraufmobileapp.entity.ExerciseResult;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseResultReqDTO;

public interface ExerciseResultMapper {

    ExerciseResult toEntity(ExerciseResultReqDTO dto);

    ExerciseResultDTO toDTO(ExerciseResult result);

}
