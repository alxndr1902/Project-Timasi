package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.company.CompanyResponseDTO;
import com.zezame.timasi.dto.company.CreateCompanyRequestDTO;
import com.zezame.timasi.dto.company.UpdateCompanyRequestDTO;
import com.zezame.timasi.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getCompanies() {
        List<CompanyResponseDTO> responses = companyService.getCompanies();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyResponseDTO> getCompanyById(
            @PathVariable String id) {
        CompanyResponseDTO response = companyService.getCompanyById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CreateResponseDTO> createCompany(
            @Valid @RequestBody CreateCompanyRequestDTO request) {
        CreateResponseDTO response = companyService.createCompany(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateResponseDTO> updateCompany(@PathVariable String id,
                                                           @Valid @RequestBody UpdateCompanyRequestDTO request) {
        UpdateResponseDTO response = companyService.updateCompany(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponseDTO> deleteCompany(@PathVariable String id) {
        CommonResponseDTO response = companyService.deleteCompany(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
