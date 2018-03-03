package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.dto.RoleDto;
import com.miner.mono.enums.RoleName;
import com.miner.mono.persistence.model.ApplicationUser;
import com.miner.mono.persistence.model.Role;
import com.miner.mono.persistence.model.UserShare;
import com.miner.mono.persistence.repository.ApplicationUserRepository;
import com.miner.mono.persistence.repository.RoleRepository;
import com.miner.mono.persistence.repository.UserShareRepository;
import com.miner.mono.security.Credentials;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final RoleRepository roleRepository;
    private final UserShareRepository userShareRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository,
                                  RoleRepository roleRepository,
                                  UserShareRepository userShareRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleRepository = roleRepository;
        this.userShareRepository = userShareRepository;
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

    @Transactional
    public void delete(ApplicationUserDto userDto) {
        applicationUserRepository.delete(userDto.getId());
    }

    private ApplicationUserDto saveUser(Credentials credentials, RoleName roleName) {
        Role userRole = roleRepository.findByName(roleName);
        ApplicationUser user = applicationUserRepository.save(new ApplicationUser(
                credentials.getUsername(),
                bCryptPasswordEncoder.encode(credentials.getPassword()),
                Collections.singletonList(userRole)));
        userShareRepository.save(new UserShare(
                user,
                BigDecimal.ZERO,
                ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime()
        ));
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
