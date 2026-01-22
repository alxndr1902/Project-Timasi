package com.zezame.timasi.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProductCustomerRequestDTO {
    @NotBlank(message = "Customer is Required")
    @Size(min = 36, max = 36)
    private String customerId;

    @NotEmpty(message = "Product Is Required")
    @Size(min = 36, max = 36)
    private List<String> productIds;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductId(List<String> productId) {
        this.productIds = productId;
    }
}
