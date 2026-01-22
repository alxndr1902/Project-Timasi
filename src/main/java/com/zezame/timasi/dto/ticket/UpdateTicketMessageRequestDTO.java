package com.zezame.timasi.dto.ticket;

import jakarta.validation.constraints.NotBlank;

public class UpdateTicketMessageRequestDTO {
    @NotBlank(message = "Message Is Required")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
