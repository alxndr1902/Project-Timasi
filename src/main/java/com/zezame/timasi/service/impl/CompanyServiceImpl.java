package com.zezame.timasi.service.impl;

import com.zezame.timasi.constant.Message;
import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.company.CompanyResponseDTO;
import com.zezame.timasi.dto.company.CreateCompanyRequestDTO;
import com.zezame.timasi.dto.company.UpdateCompanyRequestDTO;
import com.zezame.timasi.exceptiohandler.exception.DataIntegrationException;
import com.zezame.timasi.exceptiohandler.exception.DuplicateException;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.Company;
import com.zezame.timasi.repository.CompanyRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyResponseDTO> getCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyResponseDTO> dtos = companies.stream()
                .map(this::mapToDto)
                .toList();
        return dtos;
    }

    @Override
    public CompanyResponseDTO getCompanyById(String id) {
        var company = findCompanyById(id);
        var dto =  mapToDto(company);
        return dto;
    }

    @Override
    public CreateResponseDTO createCompany(CreateCompanyRequestDTO request) {
        if (companyRepository.existsByName(request.getName())) {
            throw new DuplicateException("Company Name Is Not Available");
        }

        if (companyRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicateException("Phone Number Is Not Available");
        }

        var company = new Company();
        company.setName(request.getName());
        company.setPhoneNumber(request.getPhoneNumber());
        Company savedCompany = companyRepository.save(prepareCreate(company));
        return new CreateResponseDTO(savedCompany.getId(), Message.CREATED.getName());
    }

    @Override
    public UpdateResponseDTO updateCompany(String id, UpdateCompanyRequestDTO request) {
        var company = findCompanyById(id);

        if (!company.getVersion().equals(request.getVersion())) {
            throw new DataIntegrationException("Error Updating Company, Please Refresh The Page");
        }

        if (!company.getName().equals(request.getName())) {
            companyRepository.findByName(request.getName())
                    .ifPresent(c -> {
                        throw new DuplicateException("Company Name Is Not Available");
                    });
        }

        if (!company.getPhoneNumber().equals(request.getPhoneNumber())) {
            companyRepository.findByPhoneNumber(request.getPhoneNumber())
                    .ifPresent(c -> {
                        throw new DuplicateException("Phone Number Is Not Available");
                    });
        }

        company.setName(request.getName());
        company.setPhoneNumber(request.getPhoneNumber());
        Company updatedCompany = companyRepository.saveAndFlush(prepareUpdate(company));
        return new UpdateResponseDTO(updatedCompany.getId(), Message.UPDATED.getName(), updatedCompany.getVersion());
    }

    @Override
    public CommonResponseDTO deleteCompany(String id) {
        var company = findCompanyById(id);
        companyRepository.delete(company);
        return new CommonResponseDTO(Message.DELETED.getName());
    }

    private CompanyResponseDTO mapToDto(Company company) {
        var dto = new CompanyResponseDTO(
                company.getId(), company.getName(),
                company.getPhoneNumber(), company.getVersion());
        return dto;
    }

    private Company findCompanyById(String id) {
        var companyId = convertToUUID(id);
        var company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company Not Found"));

        return company;
    }
}
