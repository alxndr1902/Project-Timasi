package com.zezame.timasi.dto;

import java.util.UUID;

public class CreateResponseDTO {
    private final UUID id;
    private final String message;

    public CreateResponseDTO(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
