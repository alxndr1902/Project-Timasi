package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.customer.ProductCustomerRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/product-customers")
public class ProductCustomerController {
    @PostMapping
    public ResponseEntity<CommonResponseDTO> createProductCustomer(@Valid @RequestBody ProductCustomerRequestDTO request) {
        return null;
    }

    @PutMapping
    public ResponseEntity<CommonResponseDTO> updateProductCustomer(@Valid @RequestBody ProductCustomerRequestDTO request) {
        return null;
    }



}
