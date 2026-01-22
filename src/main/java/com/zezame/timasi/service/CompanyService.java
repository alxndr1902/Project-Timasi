package com.zezame.timasi.service;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.company.CompanyResponseDTO;
import com.zezame.timasi.dto.company.CreateCompanyRequestDTO;
import com.zezame.timasi.dto.company.UpdateCompanyRequestDTO;

import java.util.List;

public interface CompanyService {
    List<CompanyResponseDTO> getCompanies();

    CompanyResponseDTO getCompanyById(String id);

    CreateResponseDTO createCompany(CreateCompanyRequestDTO request);

    UpdateResponseDTO updateCompany(String id, UpdateCompanyRequestDTO request);

    CommonResponseDTO deleteCompany(String id);
}
