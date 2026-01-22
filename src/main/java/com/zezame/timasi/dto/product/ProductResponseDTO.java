package com.zezame.timasi.dto.product;

import java.util.UUID;

public record ProductResponseDTO(UUID id, String name, String code, Integer version) {
}
