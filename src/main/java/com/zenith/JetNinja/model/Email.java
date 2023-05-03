package com.zenith.JetNinja.model;

public record Email(String from, String to, String subject, String body, Long date, String ip, String html) {



    @Override
    public String toString() {
        return "Email[" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", date=" + date +
                ", ip='" + ip + '\'' +
                ", html='" + html + '\'' +
                ']';
    }
}
