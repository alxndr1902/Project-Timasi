package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.customer.AssigneeCustomerRequestDTO;
import com.zezame.timasi.service.AssigneeCustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/assignee-customers")
public class AssigneeCustomerController {
    private final AssigneeCustomerService assigneeCustomerService;

    public AssigneeCustomerController(AssigneeCustomerService assigneeCustomerService) {
        this.assigneeCustomerService = assigneeCustomerService;
    }

    @PostMapping
    public ResponseEntity<CommonResponseDTO> setAssignee(@Valid @RequestBody AssigneeCustomerRequestDTO request) {
        CommonResponseDTO response = assigneeCustomerService.setAssignee(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResponseDTO> updateAssignee(@Valid @RequestBody AssigneeCustomerRequestDTO request) {
        CommonResponseDTO response =  assigneeCustomerService.updateAssignee(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
