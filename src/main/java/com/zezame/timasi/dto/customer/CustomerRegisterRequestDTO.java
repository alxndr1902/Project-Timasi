package com.zezame.timasi.dto.customer;

import com.zezame.timasi.dto.user.UserRegisterRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerRegisterRequestDTO extends UserRegisterRequestDTO {
    @NotBlank
    @Size(max = 20)
    private String identificationNumber;

    @NotBlank
    @Size(min = 36, max = 36)
    private String companyId;

    public CustomerRegisterRequestDTO() {
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
