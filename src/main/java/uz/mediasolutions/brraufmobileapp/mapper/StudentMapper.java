package uz.mediasolutions.brraufmobileapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.mediasolutions.brraufmobileapp.entity.Student;
import uz.mediasolutions.brraufmobileapp.payload.Student2DTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentDTO;
import uz.mediasolutions.brraufmobileapp.payload.StudentReqDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "username", source = "user.username")
    StudentDTO toDTO(Student student);

    @Mapping(target = "trainingCenter.id", source = "trainingCenterId")
    Student toEntity(StudentReqDTO dto);

    Student2DTO toStudent2DTO(Student student);

}
