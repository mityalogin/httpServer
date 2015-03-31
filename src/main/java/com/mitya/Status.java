package com.mitya;

public class Status {

    private int number = 0;
    private int unique = 0;
    private int active = 0;

    private static Status sample = null;

    private Status() {

    }

    public synchronized static Status getSample() {
        if (sample == null) {
            sample = new Status();
        }
        return sample;
    }
    public synchronized  String getPage()
    {
        String s="<html><p><center>Page Status</center></p></head>"
                .concat(number+"</br>"+active+"</br");
        return s;
    }
    public synchronized void increaseNumber() {
        number++;
    }

    public synchronized void increaseUnique() {
        unique++;
    }

    public synchronized void increaseActive() {
        active++;
    }

    public synchronized void reduceActive() {
        active--;

    }
}
