package com.zezame.timasi.dto;

import java.util.UUID;

public class UpdateResponseDTO {
    private final UUID id;
    private final String message;
    private final Integer version;

    public UpdateResponseDTO(UUID id, String message, Integer version) {
        this.id = id;
        this.message = message;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getVersion() {
        return version;
    }
}
