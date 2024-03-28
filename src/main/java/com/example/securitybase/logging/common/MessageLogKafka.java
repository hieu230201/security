package com.example.securitybase.logging.common;

public class MessageLogKafka {
    private String timestamp;
    private String destIp;
    private String userAuthen;
    private String sourceIp;
    private String method;
    private String level;
    private String bytesIn;
    private String bytesOut;
    private Long duration;
    private String page;
    private String servicemessageid;
    private String fulldata;
    private String servicename;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public String getUserAuthen() {
        return userAuthen;
    }

    public void setUserAuthen(String userAuthen) {
        this.userAuthen = userAuthen;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBytesIn() {
        return bytesIn;
    }

    public void setBytesIn(String bytesIn) {
        this.bytesIn = bytesIn;
    }

    public String getBytesOut() {
        return bytesOut;
    }

    public void setBytesOut(String bytesOut) {
        this.bytesOut = bytesOut;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getServicemessageid() {
        return servicemessageid;
    }

    public void setServicemessageid(String servicemessageid) {
        this.servicemessageid = servicemessageid;
    }

    public String getFulldata() {
        return fulldata;
    }

    public void setFulldata(String fulldata) {
        this.fulldata = fulldata;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }
}