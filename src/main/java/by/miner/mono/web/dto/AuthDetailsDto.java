package by.miner.mono.web.dto;

public class AuthDetailsDto {
    private final TokenDto token;
    private final UserDto user;

    public AuthDetailsDto(TokenDto token, UserDto user) {
        this.token = token;
        this.user = user;
    }

    public TokenDto getToken() {
        return token;
    }

    public UserDto getUser() {
        return user;
    }
}
