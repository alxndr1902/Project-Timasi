package com.zezame.timasi.service.impl;

import com.zezame.timasi.dto.ticket.TicketHistoryResponseDTO;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
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
        List<TicketHistoryResponseDTO> dtos = ticketHistories.stream()
                .map(this::mapToDto)
                .toList();
        return dtos;
    }

    @Override
    public TicketHistoryResponseDTO getTicketHistoryById(String id) {
        var ticketHistoryId = convertToUUID(id);
        var ticketHistory = ticketHistoryRepository.findById(ticketHistoryId)
                .orElseThrow(() -> new NotFoundException("History Not Found"));
        var dto = mapToDto(ticketHistory);
        return dto;
    }

    private TicketHistoryResponseDTO mapToDto(TicketHistory ticketHistory) {
        TicketHistoryResponseDTO dto = new TicketHistoryResponseDTO(
                ticketHistory.getId(), ticketHistory.getTicket().getId(),
                ticketHistory.getTicket().getTitle(), ticketHistory.getTicketStatus().getCode());

        return dto;
    }
}
