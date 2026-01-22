package com.zezame.timasi.model.company;

import com.zezame.timasi.model.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "assignees_customers")
public class AssigneeCustomer extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    public AssigneeCustomer() {
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
