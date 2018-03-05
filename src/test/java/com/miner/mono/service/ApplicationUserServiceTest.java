package com.miner.mono.service;

import com.miner.mono.dto.ApplicationUserDto;
import com.miner.mono.enums.RoleName;
import com.miner.mono.security.Credentials;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.isA;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class ApplicationUserServiceTest extends AbstractServiceTest {
    private static final String USER_NAME = "user";
    private static final String USER_PASSWORD = "password";

    @Autowired
    private ApplicationUserService applicationUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void saveAdmin() {
        applicationUserService.saveAdmin(new Credentials(USER_NAME, USER_PASSWORD));
        assertUser(applicationUserService.findByUsername(USER_NAME), RoleName.ROLE_ADMIN);
    }

    @Test
    public void saveUser() {
        applicationUserService.saveUser(new Credentials(USER_NAME, USER_PASSWORD));
        assertUser(applicationUserService.findByUsername(USER_NAME), RoleName.ROLE_USER);
    }

    @Test
    public void delete() {
        applicationUserService.saveUser(new Credentials(USER_NAME, USER_PASSWORD));
        ApplicationUserDto user = applicationUserService.findByUsername(USER_NAME);
        assertUser(user, RoleName.ROLE_USER);
        applicationUserService.delete(user.getId());
        ApplicationUserDto deletedUser = applicationUserService.findByUsername(USER_NAME);
        assertThat(deletedUser, nullValue());
    }

    private void assertUser(ApplicationUserDto user, RoleName roleAdmin) {
        assertThat(user.getId(), isA(Long.class));
        assertThat(user.getUsername(), equalTo(USER_NAME));
        assertTrue(bCryptPasswordEncoder.matches(USER_PASSWORD, user.getPassword()));
        assertThat(user.getRoles(), hasSize(1));
        assertThat(user.getRoles().get(0).getId(), isA(Long.class));
        assertThat(user.getRoles().get(0).getName(), equalTo(roleAdmin));
    }
}