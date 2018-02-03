package by.miner.mono.service;

import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.security.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Transactional(readOnly = true)
    public ApplicationUser findByUsername(String name) {
        return applicationUserRepository.findByUsername(name);
    }

    @Transactional
    public void save(Credentials credentials) {
        applicationUserRepository.save(new ApplicationUser(credentials.getUsername(), credentials.getPassword()));
    }
}
