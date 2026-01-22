package com.zezame.timasi.model.company;

import com.zezame.timasi.model.BaseModel;
import jakarta.persistence.*;

@Entity
@Table(name = "product_customers")
public class ProductCustomer extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    public ProductCustomer() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
