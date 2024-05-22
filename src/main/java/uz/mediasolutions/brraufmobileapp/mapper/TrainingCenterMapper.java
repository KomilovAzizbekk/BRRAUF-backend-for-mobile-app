package uz.mediasolutions.brraufmobileapp.mapper;

import uz.mediasolutions.brraufmobileapp.entity.TrainingCenter;
import uz.mediasolutions.brraufmobileapp.payload.TrainingCenterDTO;

public interface TrainingCenterMapper {

    TrainingCenter toEntity(TrainingCenterDTO dto);

    TrainingCenterDTO toDTO(TrainingCenter trainingCenter);

}
