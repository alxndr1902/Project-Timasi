package com.zezame.timasi.dto.ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketMessageResponseDTO {
    private UUID id;
    private String message;
    private String senderName;
    private String roleName;
    private LocalDateTime createdAt;
    private Integer version;

    public TicketMessageResponseDTO(UUID id, String message, String senderName, String roleName, LocalDateTime createdAt, Integer version) {
        this.id = id;
        this.message = message;
        this.senderName = senderName;
        this.roleName = roleName;
        this.createdAt = createdAt;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRoleName() {
        return roleName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getVersion() {
        return version;
    }
}
