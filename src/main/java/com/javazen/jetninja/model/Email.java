package com.javazen.jetninja.model;

public class Email {
    private String from;
    private  String to;
    private  String subject;
    private  String body;
    private Long date;
    private String ip;
    private String html;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Email(String from, String to, String subject, String body, Long date, String ip, String html) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.date = date;
        this.ip = ip;
        this.html = html;
    }

    public Email() {
    }

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
