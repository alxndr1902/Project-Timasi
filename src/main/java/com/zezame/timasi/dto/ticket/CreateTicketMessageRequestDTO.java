package com.zezame.timasi.dto.ticket;

import jakarta.validation.constraints.NotBlank;

public class CreateTicketMessageRequestDTO {
    @NotBlank(message = "Ticket Message Is Required")
    private String ticketMessage;

    public String getTicketMessage() {
        return ticketMessage;
    }

    public void setTicketMessage(String ticketMessage) {
        this.ticketMessage = ticketMessage;
    }
}
