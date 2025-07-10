package com.mobin.book.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {
    @NotEmpty(message = "Email is Mandatory")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotEmpty(message = "Password is Mandatory")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
