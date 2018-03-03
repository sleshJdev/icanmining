package com.miner.mono.dto;

import com.miner.mono.enums.RoleName;

import java.util.List;

public class AuthInfo {
    private Token token;
    private User user;

    public AuthInfo() {
    }

    public AuthInfo(Token token, User user) {
        this.token = token;
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    public static final class Token {
        private String name;
        private String value;

        public Token() {
        }

        public Token(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    public static final class User {
        private String username;
        private List<RoleName> roles;

        public User() {
        }

        public User(String username, List<RoleName> roles) {
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
}
