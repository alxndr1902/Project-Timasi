package com.zezame.timasi.dto.ticket;

import java.util.UUID;

public class CreateTicketResponseDTO {
    private final UUID id;
    private final String code;
    private final String message;

    public CreateTicketResponseDTO(UUID id, String code, String message) {
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
