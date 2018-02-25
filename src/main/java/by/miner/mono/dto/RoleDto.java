package by.miner.mono.dto;

import by.miner.mono.enums.RoleName;

public class RoleDto {
    private Long id;
    private RoleName name;

    public RoleDto() {
    }

    public RoleDto(Long id, RoleName name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}
