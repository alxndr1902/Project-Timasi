package com.zezame.timasi.repository;

import com.zezame.timasi.model.company.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
