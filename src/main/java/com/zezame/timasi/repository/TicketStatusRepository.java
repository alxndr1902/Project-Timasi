package com.zezame.timasi.repository;

import com.zezame.timasi.model.ticket.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, UUID> {
    Optional<TicketStatus> findByCode(String code);
}
