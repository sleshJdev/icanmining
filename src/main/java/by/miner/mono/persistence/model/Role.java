package by.miner.mono.persistence.model;

import by.miner.mono.enums.RoleName;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private RoleName name;

    public Role() {
    }

    public Role(RoleName name) {
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
