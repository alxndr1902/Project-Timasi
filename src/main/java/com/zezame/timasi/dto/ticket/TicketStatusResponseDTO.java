package com.zezame.timasi.dto.ticket;

import java.util.UUID;

public record TicketStatusResponseDTO(UUID id, String code, String name) {
}
