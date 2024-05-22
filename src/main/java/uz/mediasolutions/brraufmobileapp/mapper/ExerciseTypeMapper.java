package uz.mediasolutions.brraufmobileapp.mapper;

import org.mapstruct.Mapper;
import uz.mediasolutions.brraufmobileapp.entity.ExerciseType;
import uz.mediasolutions.brraufmobileapp.payload.ExerciseTypeDTO;

@Mapper(componentModel = "spring")
public interface ExerciseTypeMapper {

    ExerciseType toEntity(ExerciseTypeDTO exerciseTypeDTO);

    ExerciseTypeDTO toDto(ExerciseType exerciseType);

}
