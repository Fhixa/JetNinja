package com.javazen.jetninja.model;

public class GeneratedEmail {
    private String address;
    private String token;

    @Override
    public String toString() {
        return "EmailGenerate{" +
                "address='" + address + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

 public void setToken(String token) {
        this.token = token;
    }

    public GeneratedEmail() {
    }

    public GeneratedEmail(String address, String token) {
        this.address = address;
        this.token = token;
    }
}
