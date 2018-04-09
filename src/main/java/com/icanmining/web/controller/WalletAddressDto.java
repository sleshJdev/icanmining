package com.icanmining.web.controller;

public class WalletAddressDto {
    private String address;

    public WalletAddressDto(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
