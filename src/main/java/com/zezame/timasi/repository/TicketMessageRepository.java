package com.zezame.timasi.repository;

import com.zezame.timasi.model.ticket.Ticket;
import com.zezame.timasi.model.ticket.TicketHistory;
import com.zezame.timasi.model.ticket.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, UUID> {
    List<TicketMessage> findAllByTicket(Ticket ticket);
}
