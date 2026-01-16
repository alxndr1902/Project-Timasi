package com.zezame.timasi.dto.company;

import java.util.UUID;

public record CompanyResponseDTO(UUID id, String name, String phoneNumber) {
}
