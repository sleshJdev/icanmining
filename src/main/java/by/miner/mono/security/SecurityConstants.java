package by.miner.mono.security;

public interface SecurityConstants {
    String SECRET_KEY = "SecretKeyToGenJWTs";
    long EXPIRATION_TIME = 864_000_000; // 10 days
    String TOKEN_PREFIX = "X-Auth-Token ";
    String HEADER_STRING = "X-Authorization";
    String SIGN_UP_URL = "/api/sign-up";
    String SIGN_IN_URL = "/api/sign-in";
}
