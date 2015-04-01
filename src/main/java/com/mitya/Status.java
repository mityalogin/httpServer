package com.mitya;

import java.util.ArrayList;

public class Status {

    private int number = 0;
    private int unique = 0;
    private int active = 0;
    private ArrayList<Data> conne = new ArrayList<Data>();
    private ArrayList<DataIP> ipStat = new ArrayList<DataIP>();
    private ArrayList<DataURL> urlStat = new ArrayList<DataURL>();
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
        unique = ipStat.size();
        String s = "<html><p><center>Page Status</center></p></head>"
                .concat("<table border =2><tr><th>Total number of requests</th><th>Number of open connections</th>")
                .concat("<th>Number of unique queries</th></tr>")
                .concat("<tr><th>" + number + "</th><th>" + active + "</th><th>" + unique + "</th></tr></table>")
                .concat("<table border =2><tr><th>IP</th><th>URI</th><th>Timestamp</th><th>sent_Bytes</th>")
                .concat("<th>recieved_Bytes</th><th>speed</th></tr>");
        for (int i = 0; i < conne.size(); i++) {
            s += "<tr><th>" + conne.get(i).getIP() + "</th><th>" + conne.get(i).getURI() + "</th><th>" + conne.get(i).getDate()
                    .concat("</th><th>" + conne.get(i).getSent_Bytes() + "</th><th>" + conne.get(i).getRecievie_dBytes())
                    .concat("</th><th>" + conne.get(i).getSpeed() + "</th></tr>");
        }
        s += "</table><table border=2><tr><th>IP</th><th>CountIP</th><th>LastDate</th></tr>";
        for (int i = 0; i < ipStat.size(); i++) {
            s += "<tr><th>" + ipStat.get(i).getIp() + "</th><th>" + ipStat.get(i).getCount() + "</th><th>" + ipStat.get(i).getDate() + "</th>";
        }
        s += "</table><table border=2><tr><th>URL</th><th>CountURL</th></tr>";
        for (int i = 0; i < urlStat.size(); i++) {
            s += "<tr><th>" + urlStat.get(i).getURL() + "</th><th>" + urlStat.get(i).getCount() + "</th></tr>";
        }
        s += "</table";
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

    public synchronized void newIP(String ip) {
        boolean flag = true;
        for (int i = 0; i < ipStat.size(); i++) {
            if (ipStat.get(i).getIp().equals(ip)) {
                ipStat.get(i).increaseCount();
                ipStat.get(i).returnDate();
                flag = false;
            }
        }
        if (flag) {
            ipStat.add(new DataIP(ip));
        }
    }

    public synchronized void newURL(String url) {
        boolean flag = true;
        for (int i = 0; i < urlStat.size(); i++) {
            if (urlStat.get(i).getURL().equals(url)) {
                urlStat.get(i).increaseCount();
                flag = false;
            }
        }
        if (flag) {
            urlStat.add(new DataURL(url));
        }
    }
}
