package com.zezame.timasi.repository;

import com.zezame.timasi.model.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByName(String name);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Company> findByName(String name);

    Optional<Company> findByPhoneNumber(String phoneNumber);
}
