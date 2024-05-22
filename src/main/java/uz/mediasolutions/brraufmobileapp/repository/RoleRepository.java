package uz.mediasolutions.brraufmobileapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.mediasolutions.brraufmobileapp.entity.Role;
import uz.mediasolutions.brraufmobileapp.enums.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName roleName);

}
