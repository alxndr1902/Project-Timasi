package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.DeleteResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.company.CompanyResponseDTO;
import com.zezame.timasi.dto.company.CreateCompanyRequestDTO;
import com.zezame.timasi.dto.company.UpdateCompanyRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("companies")
public class CompanyController {
    @GetMapping
    public ResponseEntity<CompanyResponseDTO> getCompanies() {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(
            @PathVariable String id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createCompany(
            @Valid @RequestBody CreateCompanyRequestDTO request) {
        return null;
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateResponseDTO> updateCompany(@PathVariable String id,
                                                           @Valid @RequestBody UpdateCompanyRequestDTO request) {
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponseDTO> deleteCompany(@PathVariable String id) {
        return null;
    }
}
