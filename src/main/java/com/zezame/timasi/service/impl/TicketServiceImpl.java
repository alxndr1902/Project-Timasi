package com.zezame.timasi.service.impl;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.ticket.*;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.ticket.Ticket;
import com.zezame.timasi.repository.TicketRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketServiceImpl extends BaseService implements TicketService {
    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<TicketResponseDTO> getTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketResponseDTO> response = tickets.stream()
                .map(this::mapToDto)
                .toList();
        return response;
    }

    @Override
    public TicketResponseDTO getTicket(String id) {
        Ticket ticket = findTicketById(id);
        TicketResponseDTO response = mapToDto(ticket);
        return response;
    }

    private TicketResponseDTO mapToDto(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO(
                ticket.getId(), ticket.getCode(), ticket.getTitle(),
                ticket.getDescription(), ticket.getCustomer().getFullName(),
                ticket.getAssignee().getFullName());

        return dto;
    }

    @Override
    public CreateTicketResponseDTO createTicket(CreateTicketRequestDTO request) {
        return null;
    }

    @Override
    public UpdateResponseDTO updateTicket(String id, String statusCode) {
        return null;
    }

    @Override
    public List<TicketMessageResponseDTO> getTicketMessages(String id) {
        return List.of();
    }

    @Override
    public TicketMessageResponseDTO getTicketMessage(String ticketId, String id) {
        return null;
    }

    @Override
    public CreateResponseDTO createTicketMesage(String ticketId, CreateTicketMessageRequestDTO request) {
        return null;
    }

    @Override
    public UpdateResponseDTO updateTicketMesage(String ticketId, String id, UpdateTicketMessageRequestDTO request) {
        return null;
    }

    private Ticket findTicketById(String id) {
        UUID ticketId = convertToUUID(id);
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new NotFoundException("Ticket Not Found"));
        return ticket;
    }
}
