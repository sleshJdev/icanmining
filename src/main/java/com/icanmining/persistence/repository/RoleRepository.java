package com.icanmining.persistence.repository;

import com.icanmining.enums.RoleName;
import com.icanmining.persistence.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(RoleName name);
}
