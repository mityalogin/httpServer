package com.mitya;

public class DataURL {

    private final String url;
    private int count;

    DataURL(String url) {
        this.url = url;
        this.count = 1;
    }

    public synchronized void increaseCount() {
        this.count++;
    }

    public String getURL() {
        return this.url;
    }

    public String getCount() {
        return this.count+"";
    }
}
