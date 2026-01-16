package com.zezame.timasi.model.company;

import jakarta.persistence.*;

@Entity
@Table(name = "customers")
public class Customer extends User{
    @Column(length = 20, nullable = false, unique = true)
    private String identificationNumber;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;



    public Customer() {
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
