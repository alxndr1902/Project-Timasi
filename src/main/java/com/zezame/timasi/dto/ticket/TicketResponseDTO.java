package com.zezame.timasi.dto.ticket;

import java.util.UUID;

public class TicketResponseDTO {
    private UUID id;
    private String code;
    private String title;
    private String description;
    private String customerName;
    private String assigneeName;

    public TicketResponseDTO(UUID id, String code, String title, String description, String customerName, String assigneeName) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.customerName = customerName;
        this.assigneeName = assigneeName;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAssigneeName() {
        return assigneeName;
    }
}
