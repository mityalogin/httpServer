package com.mitya;

import java.util.Date;

public class DataIP {

    private final String ip;
    private int count;
    private Date date;

    DataIP(String ip) {
        this.ip = ip;
        this.count = 1;
        this.date = new Date();
    }

    public synchronized void increaseCount() {
        count++;
    }

    public synchronized void returnDate() {
        date = new Date();
    }

    public String getIp() {
        return this.ip;
    }

    public String getCount() {
        return this.count + "";
    }

    public String getDate() {
        return this.date + "";
    }
}
