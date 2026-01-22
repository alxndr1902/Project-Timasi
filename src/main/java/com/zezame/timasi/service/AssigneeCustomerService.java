package com.zezame.timasi.service;

import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.customer.AssigneeCustomerRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AssigneeCustomerService {
    CommonResponseDTO setAssignee(@Valid @RequestBody AssigneeCustomerRequestDTO request);

    CommonResponseDTO updateAssignee(@Valid @RequestBody AssigneeCustomerRequestDTO request);
}
