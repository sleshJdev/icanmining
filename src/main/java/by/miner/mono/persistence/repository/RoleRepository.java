package by.miner.mono.persistence.repository;

import by.miner.mono.enums.RoleName;
import by.miner.mono.persistence.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(RoleName name);
}
