package com.mitya;

import java.util.Date;

public class Data {

    private final String ip;
    private final String uri;
    private final Date date;
    private final int sent_Bytes;
    private final int recieved_Bytes;
    private final double speed;

    Data(String ip, String uri, int sent_Bytes, int recieved_Bytes, double speed) {
        this.ip = ip;
        this.uri = uri;
        this.date = new Date();
        this.sent_Bytes = sent_Bytes;
        this.recieved_Bytes = recieved_Bytes;
        this.speed = speed;
    }

    Data() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getIP() {
        return ip;
    }

    public String getURI() {
        return uri;
    }

    public String getDate() {
        return date.toString();
    }

    public String getSent_Bytes() {
        return sent_Bytes + "";
    }

    public String getRecievie_dBytes() {
        return recieved_Bytes + "";
    }

    public String getSpeed() {
        return speed + "";
    }
}
