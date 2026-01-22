package com.zezame.timasi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserRequestDTO {
    @NotBlank(message = "Full Name Is Required")
    @Size(max = 50, message = "Full Name Maximum Length Is 50 Characters")
    private String fullName;

    @NotBlank(message = "Email Is Required")
    @Email(message = "Invalid Email Format")
    @Size(max = 20, message = "Email Maximum Length Is 20 Characters")
    private String email;

    @NotNull
    private Integer version;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
