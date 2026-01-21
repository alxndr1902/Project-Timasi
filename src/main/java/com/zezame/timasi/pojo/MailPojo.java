package com.zezame.timasi.pojo;

public class MailPojo {
    private String email;
    private String loanCode;

    public MailPojo(String email, String loanCode) {
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
