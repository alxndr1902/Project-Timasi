package com.zezame.timasi.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public class AssigneeCustomerRequestDTO {
    @NotBlank(message = "PIC Is Required")
    @Size(min = 36, max = 36)
    private String assigneeId;

    @NotEmpty(message = "Customer is Required")
    private List<String> customerIds;

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public List<String> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<String> customerIds) {
        this.customerIds = customerIds;
    }
}
