package com.mitya;

import java.util.Date;

public class Data {

    private String ip = "";
    private String uri = "";
    private Date date = new Date();
    private int sentBytes = 0;
    private int recievedBytes = 0;
    private double speed = 0;

    Data(String ip, String uri, int sentBytes, int recievedBytes, double speed) {
        this.ip = ip;
        this.uri = uri;
        this.date = new Date();
        this.sentBytes = sentBytes;
        this.recievedBytes = recievedBytes;
        this.speed = speed;
    }

    public String getIP() {
        return ip;
    }

    public String getURL() {
        return uri;
    }

    public String getDate() {
        return date.toString();
    }

    public String getSentBytes() {
        return sentBytes+"";
    }

    public String getRecieviedBytes() {
        return recievedBytes+"";
    }

    public String getSpeed() {
        return speed+"";
    }
}
