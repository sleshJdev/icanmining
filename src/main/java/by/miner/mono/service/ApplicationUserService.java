package by.miner.mono.service;

import by.miner.mono.dto.ApplicationUserDto;
import by.miner.mono.dto.RoleDto;
import by.miner.mono.enums.RoleName;
import by.miner.mono.persistence.model.ApplicationUser;
import by.miner.mono.persistence.model.Role;
import by.miner.mono.persistence.repository.ApplicationUserRepository;
import by.miner.mono.persistence.repository.RoleRepository;
import by.miner.mono.security.Credentials;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static java.util.stream.Collectors.toList;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  RoleRepository roleRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(readOnly = true)
    public ApplicationUserDto findByUsername(String name) {
        ApplicationUser user = applicationUserRepository.findByUsername(name);
        return toDto(user);
    }

    @Transactional
    public ApplicationUserDto saveAdmin(Credentials credentials) {
        return saveUser(credentials, RoleName.ROLE_ADMIN);
    }

    @Transactional
    public ApplicationUserDto saveUser(Credentials credentials) {
        return saveUser(credentials, RoleName.ROLE_USER);
    }

    private ApplicationUserDto saveUser(Credentials credentials, RoleName roleName) {
        Role userRole = roleRepository.findByName(roleName);
        ApplicationUser user = applicationUserRepository.save(new ApplicationUser(
                credentials.getUsername(),
                bCryptPasswordEncoder.encode(credentials.getPassword()),
                Collections.singletonList(userRole)));
        return toDto(user);
    }

    private ApplicationUserDto toDto(ApplicationUser user) {
        return new ApplicationUserDto(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(it -> new RoleDto(it.getId(), it.getName()))
                        .collect(toList()));
    }
}
