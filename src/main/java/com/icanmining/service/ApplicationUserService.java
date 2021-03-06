package com.icanmining.service;

import com.icanmining.dto.ApplicationUserDto;
import com.icanmining.dto.RoleDto;
import com.icanmining.persistence.model.ApplicationUser;
import com.icanmining.persistence.repository.ApplicationUserRepository;
import com.icanmining.persistence.repository.RoleRepository;
import com.icanmining.security.Credentials;
import com.icanmining.enums.RoleName;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Collections.singletonList;
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
        return Optional.ofNullable(user).map(this::toDto).orElse(null);
    }

    @Transactional
    public ApplicationUserDto saveAdmin(Credentials credentials) {
        return saveUser(credentials, RoleName.ROLE_ADMIN);
    }

    @Transactional
    public ApplicationUserDto saveUser(Credentials credentials) {
        return saveUser(credentials, RoleName.ROLE_USER);
    }

    @Transactional
    public void delete(Long userId) {
        applicationUserRepository.delete(userId);
    }

    private ApplicationUserDto saveUser(Credentials credentials, RoleName roleName) {
        ApplicationUser user = new ApplicationUser();
        user.setUsername(credentials.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));
        user.setRoles(singletonList(roleRepository.findByName(roleName)));
        ApplicationUser userEntity = applicationUserRepository.save(user);
        return toDto(userEntity);
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
