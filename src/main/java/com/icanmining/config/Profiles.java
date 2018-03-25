package com.icanmining.config;

public final class Profiles {
    public static final String PROD = "prod";
    public static final String DEV = "dev";
    public static final String TEST = "test";

    private Profiles() {
        throw new AssertionError("Cannot be instantiated!");
    }
}
