package com.zezame.timasi.pojo;

public class CreateTicketMailPojo {
    private String email;
    private String loanCode;

    public CreateTicketMailPojo(String email, String loanCode) {
        this.email = email;
        this.loanCode = loanCode;
    }

    public String getEmail() {
        return email;
    }

    public String getLoanCode() {
        return loanCode;
    }
}
