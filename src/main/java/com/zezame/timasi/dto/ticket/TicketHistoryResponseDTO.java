package com.zezame.timasi.dto.ticket;

import com.zezame.timasi.model.ticket.TicketStatus;

import java.util.UUID;

public record TicketHistoryResponseDTO(UUID id, UUID ticketId, String ticketTitle, String ticketStatusCode) {
}
