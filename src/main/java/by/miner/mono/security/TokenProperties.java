package by.miner.mono.security;

public class TokenProperties {
    private long maxAgeSeconds;
    private String secret;
    private String tokenPrefix = "X-Auth-Token ";
    private String headerString = "X-Authorization";
    private String signUpUrl = "/api/sign-up";
    private String signInUrl = "/api/sign-in";

    public TokenProperties(long maxAgeSeconds, String secret) {
        this.maxAgeSeconds = maxAgeSeconds;
        this.secret = secret;
    }

    public TokenProperties() {
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }
    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }

    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
