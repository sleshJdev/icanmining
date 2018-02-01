package by.miner.mono.web.dto;

public final class WalletDto {
    private final String address;

    public WalletDto(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
