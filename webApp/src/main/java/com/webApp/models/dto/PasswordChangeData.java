package com.webApp.models.dto;

public class PasswordChangeData {
    private Long id;
    private String password;

    public PasswordChangeData(){}

    public PasswordChangeData(Long id, String password) {
        this.id = id;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordChangeData{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
