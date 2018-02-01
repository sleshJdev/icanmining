package by.miner.mono.web.dto;

public final class TokenDto {
    private final String name;
    private final String value;

    public TokenDto(String name, String value) {
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
