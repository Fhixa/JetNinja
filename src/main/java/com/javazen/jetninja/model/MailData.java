package com.javazen.jetninja.model;

import java.util.List;

public class MailData {
    private List<Email> email;

    public List<Email> getEmail() {
        return email;
    }

    public void setEmail(List<Email> email) {
        this.email = email;
    }

    public MailData(List<Email> email) {
        this.email = email;
    }

    public MailData() {
    }

    @Override
    public String toString() {
        return "MailData{" +
                "email=" + email +
                '}';
    }
}
