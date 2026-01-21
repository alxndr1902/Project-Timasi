package com.zezame.timasi.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerUpdateRequestDTO {
    @NotBlank(message = "Customer Address Is Required")
    @Size(max = 100, message = "Customer Address Maximum Length Is 100 Characters")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
