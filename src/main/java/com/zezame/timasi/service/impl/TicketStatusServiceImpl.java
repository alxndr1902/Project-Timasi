package com.zezame.timasi.service.impl;

import com.zezame.timasi.dto.ticket.TicketStatusResponseDTO;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.ticket.TicketStatus;
import com.zezame.timasi.repository.TicketStatusRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.TicketStatusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketStatusServiceImpl extends BaseService implements TicketStatusService {
    private final TicketStatusRepository ticketStatusRepository;

    public TicketStatusServiceImpl(TicketStatusRepository ticketStatusRepository) {
        this.ticketStatusRepository = ticketStatusRepository;
    }

    @Override
    public List<TicketStatusResponseDTO> getTicketStatus() {
        List<TicketStatus> ticketStatus = ticketStatusRepository.findAll();
        List<TicketStatusResponseDTO> dtos = ticketStatus.stream()
                .map(this::mapToDto)
                .toList();
        return dtos;
    }

    @Override
    public TicketStatusResponseDTO getTicketStatus(String id) {
        var ticketStatusId = convertToUUID(id);
        var ticketStatus = ticketStatusRepository.findById(ticketStatusId)
                .orElseThrow(() -> new NotFoundException("Ticket Status Is Not Found"));
        TicketStatusResponseDTO dto = mapToDto(ticketStatus);
        return dto;
    }

    private TicketStatusResponseDTO mapToDto(TicketStatus ticketStatus) {
        var dto = new TicketStatusResponseDTO(
                ticketStatus.getId(), ticketStatus.getCode(), ticketStatus.getName());

        return dto;
    }
}
