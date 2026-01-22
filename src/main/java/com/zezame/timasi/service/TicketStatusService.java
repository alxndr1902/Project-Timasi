package com.zezame.timasi.service;

import com.zezame.timasi.dto.ticket.TicketStatusResponseDTO;

import java.util.List;

public interface TicketStatusService {
    List<TicketStatusResponseDTO> getTicketStatus();

    TicketStatusResponseDTO getTicketStatus(String id);
}
