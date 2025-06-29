package com.microservice.auth_service.dto;

public class EmailDTO {

    private String email;
    private String activationToken;

    public EmailDTO() {
    }

    public EmailDTO(String email, String activationToken) {
        this.email = email;
        this.activationToken = activationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }
}
