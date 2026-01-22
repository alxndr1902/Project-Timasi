package com.zezame.timasi.service.impl;

import com.zezame.timasi.dto.ticket.TicketHistoryResponseDTO;
import com.zezame.timasi.model.ticket.TicketHistory;
import com.zezame.timasi.repository.TicketHistoryRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.TicketHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketHistoryServiceImpl extends BaseService implements TicketHistoryService {
    private final TicketHistoryRepository ticketHistoryRepository;

    public TicketHistoryServiceImpl(TicketHistoryRepository ticketHistoryRepository) {
        this.ticketHistoryRepository = ticketHistoryRepository;
    }

    @Override
    public List<TicketHistoryResponseDTO> getTicketHistoriesByCustomerId(String customerId) {
        List<TicketHistory> ticketHistories = ticketHistoryRepository.findAll();
        List<TicketHistoryResponseDTO> responses = ticketHistories.stream()
                .map(this::mapToDto)
                .toList();
        return responses;
    }

    @Override
    public TicketHistoryResponseDTO getTicketHistoryById(String id) {
        return null;
    }

    private TicketHistoryResponseDTO mapToDto(TicketHistory ticketHistory) {
        TicketHistoryResponseDTO dto = new TicketHistoryResponseDTO(
                ticketHistory.getId(), ticketHistory.getTicket().getId(),
                ticketHistory.getTicket().getTitle(), ticketHistory.getTicketStatus().getCode());

        return dto;
    }
}
