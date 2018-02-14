package by.miner.mono.service;

import by.miner.mono.enums.RoleName;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.Role;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.persistence.repository.RoleRepository;
import by.miner.mono.security.Credentials;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final RoleRepository roleRepository;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, RoleRepository roleRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public ApplicationUser findByUsername(String name) {
        return applicationUserRepository.findByUsername(name);
    }

    @Transactional
    public void save(Credentials credentials) {
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER);
        applicationUserRepository.save(new ApplicationUser(
                credentials.getUsername(),
                credentials.getPassword(),
                Collections.singletonList(userRole)));
    }
}
