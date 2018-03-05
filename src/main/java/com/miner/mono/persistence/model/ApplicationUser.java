package com.miner.mono.persistence.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ApplicationUser {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserShare userShare;

    public ApplicationUser() {
    }

    public ApplicationUser(String username, String password, List<Role> roles, UserShare userShare) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.userShare = userShare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public UserShare getUserShare() {
        return userShare;
    }

    public void setUserShare(UserShare userShare) {
        this.userShare = userShare;
    }
}
