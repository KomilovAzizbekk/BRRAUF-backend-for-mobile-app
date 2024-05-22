package uz.mediasolutions.brraufmobileapp.mapper;

import uz.mediasolutions.brraufmobileapp.entity.Exercise;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseDTO;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseReqDTO;

public interface ExerciseMapper {

    Exercise toEntity(ExerciseReqDTO dto);

    ExerciseDTO toDTO(Exercise exercise);

}
