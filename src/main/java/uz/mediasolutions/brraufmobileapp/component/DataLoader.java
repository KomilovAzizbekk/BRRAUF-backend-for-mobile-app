package uz.mediasolutions.brraufmobileapp.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mediasolutions.brraufmobileapp.entity.Role;
import uz.mediasolutions.brraufmobileapp.entity.User;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;
import uz.mediasolutions.brraufmobileapp.repository.RoleRepository;
import uz.mediasolutions.brraufmobileapp.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always")) {
            addRole();
            addAdmin();
        }

    }

    private void addRole() {
        for (RoleName value : RoleName.values()) {
            roleRepository.save(new Role(value));
        }
    }


    public void addAdmin() {
        User superAdmin = User.builder()
                .role(roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN))
                .username("superadmin")
                .password(passwordEncoder.encode("Qwerty123@"))
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .enabled(true)
                .build();

        userRepository.save(superAdmin);
    }
}
