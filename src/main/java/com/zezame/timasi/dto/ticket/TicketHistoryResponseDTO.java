package com.zezame.timasi.dto.ticket;


import java.util.UUID;

public record TicketHistoryResponseDTO(UUID id, UUID ticketId, String ticketTitle, String ticketStatusCode) {
}
