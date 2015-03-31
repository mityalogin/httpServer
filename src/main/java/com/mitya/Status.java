package com.mitya;

import java.util.ArrayList;

public class Status {

    private int number = 0;
    private int unique = 0;
    private int active = 0;
    private ArrayList<Data> conne = new ArrayList<Data>();
    private static Status sample = null;

    private Status() {

    }

    public synchronized static Status getSample() {
        if (sample == null) {
            sample = new Status();
        }
        return sample;
    }

    public synchronized String getPage() {
        String s = "<html><p><center>Page Status</center></p></head>"
                .concat("<table border =2><tr><th>Total number of requests</th><th>Number of open connections</th>")
                .concat("<th>Number of unique queries</th></tr>")
                .concat("<tr><th>" + number + "</th><th>" + active + "</th><th>" + unique + "</th></tr></table>")
                .concat("<table border =2><tr><th>IP</th><th>URI</th><th>Timestamp</th><th>sent_Bytes</th>")
                .concat("<th>recieved_Bytes</th><th>send_speed</th><th>recieved_speed</th></tr>");
        for(int i=0;i<conne.size();i++)
                s+="<tr><th>"+conne.get(i).getIP()+"</th><th>"+conne.get(i).getURI()+"</th><th>"+conne.get(i).getDate()
                        .concat("</th><th>"+conne.get(i).getSent_Bytes()+"</th><th>"+conne.get(i).getRecievie_dBytes())
                        .concat("</th><th>"+conne.get(i).getSend_speed()+"</th><th>"+conne.get(i).getRecieved_speed()+"</th></tr>");
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

    public synchronized void addConn(Data value) {
        if (conne.size() > 15) {
            conne.remove(0);
        }
        conne.add(value);
    }
}
