package com.zezame.timasi.model.ticket;

import com.zezame.timasi.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ticket_status")
public class TicketStatus extends BaseModel {
    @Column(length = 10, nullable = false, unique = true)
    private String code;

    @Column(length = 20, nullable = false, unique = true)
    private String name;

    public TicketStatus() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
