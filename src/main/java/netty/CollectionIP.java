/**
 * 
 */
package netty;

import java.util.Date;

/**
 * @author Pogorelov
 *
 */


/*
 * I store information about one IP here.
 */
public class CollectionIP {
	private String ip = "";
	private String uri = "";
	private Date date = new Date();
	private int sentBytes = 0;
	private int recievedBytes = 0;
	private double speed = 0;
	
	CollectionIP(String ip, String uri, int s_b, int r_b, double speed) {
		this.ip = ip;
		this.uri = uri;
		this.date = new Date();
		this.sentBytes = s_b;
		this.recievedBytes = r_b;
		this.speed = speed;
	}
	
	public String toString() {
		return "" + ip
		   + " " + uri
		   + " " + date
		   + " " + sentBytes
		   + " " + recievedBytes
		   + " " + speed + "<br>";
	}
	
	public String getIP() {
		return ip + "";
	}
	public String getURL() {
		return uri + "";
	}
	public String getDate() {
		return date + "";
	}
	public String getSentBytes() {
		return sentBytes + "";
	}
	public String getRecieviedBytes() {
		return recievedBytes + "";
	}
	public String getSpeed() {
		return speed + "";
	}
}
