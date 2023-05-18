package com.webApp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Token {
    private String data;
    private Instant issuedAt;
    private Instant expiration;
    private Instant lastActivity;
    private long clientId;
    private final int TOKEN_TIMEOUT_MINUTES = 30;


    public Token(String data, Instant issuedAt, Instant expiration, long clientId) {
        this.data = data;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
        this.lastActivity = issuedAt;
        this.clientId = clientId;
    }

    public String getData() {
        return data;
    }
    public Instant getEndDate() {
        Instant lastActivityTimeout = lastActivity.plus(TOKEN_TIMEOUT_MINUTES, ChronoUnit.MINUTES);
//        System.out.println("Now : " + Instant.now());
//        System.out.println("Expiration : " + expiration);
//        System.out.println("Timeout : " + lastActivityTimeout);
        return lastActivityTimeout.isBefore(expiration) ? lastActivityTimeout:expiration;
    }

    public boolean hasExpired(){
        return Instant.now().isAfter(getEndDate());
    }
    public void updateLastActivity(){
        lastActivity = Instant.now();
    }
    public long getClientId() {
        return clientId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(data, token.data);
    }
    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
