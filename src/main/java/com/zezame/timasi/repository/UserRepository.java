package com.zezame.timasi.repository;

import com.zezame.timasi.model.company.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByRoleCode(String roleCode);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByIdentificationNumber(String identificationNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findByIdAndRoleCode(UUID id, String roleCode);

    String RoleCode(String roleCode);
}
