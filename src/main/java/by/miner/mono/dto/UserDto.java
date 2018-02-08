package by.miner.mono.dto;

import by.miner.mono.enums.RoleName;

import java.util.List;

public final class UserDto {
    private final String username;
    private final List<RoleName> roles;

    public UserDto(String username, List<RoleName> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<RoleName> getRoles() {
        return roles;
    }
}
