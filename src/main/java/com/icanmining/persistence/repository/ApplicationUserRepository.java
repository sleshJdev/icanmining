package com.icanmining.persistence.repository;

import com.icanmining.persistence.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String name);
}
