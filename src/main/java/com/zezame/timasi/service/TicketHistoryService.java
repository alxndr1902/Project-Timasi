package com.zezame.timasi.service;

import com.zezame.timasi.dto.ticket.TicketHistoryResponseDTO;

import java.util.List;

public interface TicketHistoryService {
    List<TicketHistoryResponseDTO> getTicketHistoriesByCustomerId(String customerId);

    TicketHistoryResponseDTO getTicketHistoryById(String id);
}
