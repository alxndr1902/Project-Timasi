package com.zezame.timasi.service;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.ticket.*;

import java.util.List;

public interface TicketService {
    List<TicketResponseDTO> getTickets();

    TicketResponseDTO getTicket(String id);

    CreateTicketResponseDTO createTicket(CreateTicketRequestDTO request);

    UpdateResponseDTO  updateTicket(String request, String statusCode);

    List<TicketMessageResponseDTO> getTicketMessages(String ticketId);

    TicketMessageResponseDTO getTicketMessage(String id);

    CreateResponseDTO createTicketMesage(String ticketId, CreateTicketMessageRequestDTO request);

    UpdateResponseDTO updateTicketMesage(String id, UpdateTicketMessageRequestDTO request);
}
