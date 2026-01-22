package com.zezame.timasi.dto;

public class CommonResponseDTO {
    private final String message;

    public CommonResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
