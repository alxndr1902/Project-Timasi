package com.zezame.timasi.repository;

import com.zezame.timasi.model.ticket.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketMessageRepository extends JpaRepository<TicketHistory, UUID> {
}
