package com.zezame.timasi.service;

import com.zezame.timasi.exceptiohandler.exception.InvalidUUIDException;
import com.zezame.timasi.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public abstract class BaseService {

    protected PrincipalService principalService;

    protected <T extends BaseModel> T prepareCreate(T model) {
        model.setId(UUID.randomUUID());
        model.setCreatedAt(LocalDateTime.now());
        model.setCreatedBy(UUID.fromString(principalService.getPrincipal().getId()));
        return model;
    }

    protected <T extends BaseModel> T prepareUpdate(T model) {
        model.setUpdatedAt(LocalDateTime.now());
        model.setUpdatedBy(UUID.fromString(principalService.getPrincipal().getId()));
        return model;
    }

    protected UUID convertToUUID(String request) {
        try {
            return UUID.fromString(request);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException("Invalid UUID");
        }
    }

    protected LocalDateTime getDate(String request) {
        try {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime result = LocalDateTime.parse(request, format);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Invalid Format");
        }
    }

    protected String generateRandomAlphaNumeric() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int index = random.nextInt(chars.length());
            result.append(chars.charAt(index));
        }
        return result.toString();
    }

    @Autowired
    private void setPrincipal(PrincipalService principalService) {
        this.principalService = principalService;
    }
}
