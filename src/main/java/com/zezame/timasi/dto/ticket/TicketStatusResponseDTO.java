package com.zezame.timasi.dto.ticket;

import java.util.UUID;

public class TicketStatusResponseDTO {
    private UUID id;
    private String code;
    private String name;

    public TicketStatusResponseDTO(UUID id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
