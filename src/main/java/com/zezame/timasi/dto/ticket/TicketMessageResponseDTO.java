package com.zezame.timasi.dto.ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketMessageResponseDTO(UUID id, String message, String senderName,
                                       String roleName, LocalDateTime createdAt, Integer version) {
}
