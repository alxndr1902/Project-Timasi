package com.zezame.timasi.dto;

public class ErrorResponseDTO<T> {
    private final T message;

    public ErrorResponseDTO(T message) {
        this.message = message;
    }

    public T getMessage() {
        return message;
    }
}