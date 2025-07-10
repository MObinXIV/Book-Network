package com.mobin.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
    @NotEmpty(message = "First name is Mandatory")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotEmpty(message = "Last name is Mandatory")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is Mandatory")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotEmpty(message = "Password is Mandatory")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    public RegistrationRequest() {
    }

    public RegistrationRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
