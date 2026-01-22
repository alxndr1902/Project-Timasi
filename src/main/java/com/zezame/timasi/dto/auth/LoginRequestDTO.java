package com.zezame.timasi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email Is Required")
    @Size(max = 20, message = "Email Maximum Length Is 20 Characters")
    private String email;

    @NotBlank(message = "Password Is Required")
    @Size(max = 200, message = "Password Maximum Length Is 50 Characters")
    private String password;

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
