package com.zezame.timasi.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateProductRequestDTO{
    @NotBlank(message = "Product Code Is Required")
    @Size(max = 20, message = "Product Code Maximum Length Is 20 Characters")
    private String code;

    @NotBlank(message = "Product Name Is Required")
    @Size(max = 50, message = "Product Name Maximum Length Is 50 Characters")
    private String name;

    @NotNull
    private Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
