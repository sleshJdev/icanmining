package by.miner.mono.persistence.repository;

import by.miner.mono.persistence.model.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String name);
}
