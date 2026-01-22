package com.zezame.timasi.dto.company;

import java.util.UUID;

public class CompanyResponseDTO {
    private final UUID id;
    private final String name;
    private final String phoneNumber;
    private final Integer version;

    public CompanyResponseDTO(UUID id, String name, String phoneNumber, Integer version) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getVersion() {
        return version;
    }
}
