package com.mitya;

import java.util.Date;

public class Data {

    private final String ip;
    private final String uri;
    private final Date date;
    private final int sent_Bytes;
    private final int recieved_Bytes;
    private final double send_speed;
    private final double recieved_speed;

    Data(String ip, String uri, int sent_Bytes, int recieved_Bytes, double send_speed,double recieved_speed) {
        this.ip = ip;
        this.uri = uri;
        this.date = new Date();
        this.sent_Bytes = sent_Bytes;
        this.recieved_Bytes = recieved_Bytes;
        this.send_speed = send_speed;
        this.recieved_speed=recieved_speed;
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

    public String getSend_speed() {
        return send_speed + "";
    }
     public String getRecieved_speed() {
        return recieved_speed + "";
    }
}
