package com.zezame.timasi.dto.user;

import java.util.UUID;

public record UserResponseDTO(UUID id, String fullName, String email, String phoneNumber, String roleName, Integer version) {
}
