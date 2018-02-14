package by.miner.mono.dto;

import by.miner.mono.enums.RoleName;

import java.util.List;

public class AuthInfo {
    private final Token token;
    private final User user;

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
        private final String name;
        private final String value;

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
        private final String username;
        private final List<RoleName> roles;

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
