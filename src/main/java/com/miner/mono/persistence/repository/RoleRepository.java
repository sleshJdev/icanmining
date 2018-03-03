package com.miner.mono.persistence.repository;

import com.miner.mono.enums.RoleName;
import com.miner.mono.persistence.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(RoleName name);
}
