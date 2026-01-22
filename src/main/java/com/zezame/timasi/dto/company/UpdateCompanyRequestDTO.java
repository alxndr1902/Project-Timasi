package com.zezame.timasi.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateCompanyRequestDTO{
    @NotBlank(message = "Company Name Is Required")
    @Size(max = 50, message = "Company Name Maximum Length Is 50 Characters")
    private String name;

    @NotBlank(message = "Company Phone Number Is Required")
    @Size(max = 20, message = "Company Phone Number Maximum Length Is 20 Characters")
    private String phoneNumber;

    @NotNull
    private Integer version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
