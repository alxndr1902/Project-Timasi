package com.zezame.timasi.model.ticket;

import com.zezame.timasi.model.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "ticket_histories")
public class TicketHistory extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "ticket_status_id", nullable = false)
    private  TicketStatus ticketStatus;
}
