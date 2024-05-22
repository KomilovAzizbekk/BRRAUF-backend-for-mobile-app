package uz.mediasolutions.brraufmobileapp.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mediasolutions.brraufmobileapp.entity.*;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.payload.TrainingCenterDTO;
import uz.mediasolutions.brraufmobileapp.repository.*;

@Component
@RequiredArgsConstructor
public class TrainingCenterMapperImpl implements TrainingCenterMapper {

    private final PasswordEncoder passwordEncoder;
    private final TrainingCenterRepository trainingCenterRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public TrainingCenter toEntity(TrainingCenterDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(roleRepository.findByName(RoleName.ROLE_ADMIN))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        User saved = userRepository.save(user);

        return TrainingCenter.builder()
                .name(dto.getName())
                .info(dto.getInfo())
                .user(saved)
                .build();
    }

    @Override
    public TrainingCenterDTO toDTO(TrainingCenter trainingCenter) {
        if (trainingCenter == null) {
            return null;
        }

        return TrainingCenterDTO.builder()
                .id(trainingCenter.getId())
                .name(trainingCenter.getName())
                .info(trainingCenter.getInfo())
                .username(trainingCenter.getUser().getUsername())
                .build();
    }
}
