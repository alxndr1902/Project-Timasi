package com.zezame.timasi.dto.ticket;


import java.util.UUID;

public class TicketHistoryResponseDTO {
    private UUID id;
    private UUID ticketId;
    private String ticketTitle;
    private String ticketStatusCode;

    public TicketHistoryResponseDTO(UUID id, UUID ticketId, String ticketTitle, String ticketStatusCode) {
        this.id = id;
        this.ticketId = ticketId;
        this.ticketTitle = ticketTitle;
        this.ticketStatusCode = ticketStatusCode;
    }

    public UUID getId() {
        return id;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public String getTicketStatusCode() {
        return ticketStatusCode;
    }
}
