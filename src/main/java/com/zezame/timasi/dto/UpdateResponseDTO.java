package com.zezame.timasi.dto;

import java.util.UUID;

public record UpdateResponseDTO(UUID id, String message, Integer version) {
}
