package com.zezame.timasi.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateProductRequestDTO {
    @NotBlank
    @Size(max = 20)
    private String code;

    @NotBlank
    @Size(max = 50)
    private String name;

    public CreateProductRequestDTO() {
    }

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
