package com.zezame.timasi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterRequestDTO {
    @NotBlank(message = "User Full Name Is Required")
    @Size(max = 50, message = "User Full Name Maximum Length Is 50 Characters")
    private String fullName;

    @Email(message = "Invalid Email Format")
    @NotBlank(message = "User Email Is Required")
    @Size(max = 20, message = "User Email Maximum Length Is 20 Characters")
    private String email;

    @NotBlank(message = "User Phone Number Is Required")
    @Size(max = 20, message = "User Phone Number Maximum Length Is 20 Characters")
    private String phoneNumber;

    @NotBlank(message = "User Password Is Required")
    @Size(max = 50, message = "User Password Maximum Length Is 50 Characters")
    private String password;

    @NotBlank(message = "User Role Is Required")
    @Size(min = 36,  max = 36)
    private String roleId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
