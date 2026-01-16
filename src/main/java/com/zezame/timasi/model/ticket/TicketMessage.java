package com.zezame.timasi.model.ticket;

import com.zezame.timasi.model.BaseModel;
import com.zezame.timasi.model.company.User;
import jakarta.persistence.*;
import org.hibernate.Length;

@Entity
@Table(name = "ticket_messages")
public class TicketMessage extends BaseModel {
    @Column(length = Length.LONG32, nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TicketMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
