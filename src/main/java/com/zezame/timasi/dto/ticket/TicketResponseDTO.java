package com.zezame.timasi.dto.ticket;

import java.util.UUID;

public record TicketResponseDTO(UUID id, String code, String title,
                                String description, String customerName, String assigneeName) {
}
