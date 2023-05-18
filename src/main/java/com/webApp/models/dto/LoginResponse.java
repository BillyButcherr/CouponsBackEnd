package com.webApp.models.dto;

public class LoginResponse {
    private String token;
    private long clientId;

    public LoginResponse(String token, long clientId) {
        this.token = token;
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", clientId=" + clientId +
                '}';
    }
}
