package com.zezame.timasi.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProductRequestDTO {
    @NotBlank(message = "Product Code Is Required")
    @Size(max = 20, message = "Product Code Maximum Length Is 20 Characters")
    private String code;

    @NotBlank(message = "Product Name Is Required")
    @Size(max = 50, message = "Product Name Maximum Length Is 50 Characters")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
