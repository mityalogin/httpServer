/**
 * 
 */
package netty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Pogorelov
 *
 */

/**
 * Singleton design pattern 
 * Class below is responsible for all the information about IP
 */
public final class RequestStatistics {
	//The value 'instance' hold a reference to a single instance
	private static RequestStatistics instance = null;

	/*
	 * I store information about IP in an array listIP
	 */
	private List<CollectionIP> listIP = new ArrayList<>();
	private Set<String> uniqueIP = new HashSet<>();
	
	//Save url, number him and sort
	private Map<String, Integer> countURL = new TreeMap<>();
	private long numberQuery = 0;
	private long numberActive = 0;
	//Throws exception on the first iteration if listIP is empty
	private String firstIP;
	
	private RequestStatistics() {
	}
	
	//The only way how to take link to this instance
	public synchronized static RequestStatistics getInstance() {
		if (instance == null) {
			instance = new RequestStatistics();
		}
		return instance;
	}
	
	
	/*Sorry about that. I would like create table via JSP,
	 * but I didn't have any time to study this things.
	 * It so was new information for me. I just try.
	 */
	public synchronized String getReport() {
		String lastDate = (String) (listIP.size() > 0 ? listIP.get(listIP.size()-1).getDate(): new Date()+"");
		String lastIP = getFirstIP();
		if(listIP.size() >= 1) {
			lastIP = listIP.get(listIP.size()-1).getIP();
		}
		String result = "<html><head><center><font size=10>Welcome to status query</font></center></head>"
		.concat("<table border = 1><tbody><tr><th>Total connection</th><th>Unique connection</th><th>Active connection</th><th>IP</th>")
		.concat("<th>Last Connections</th></tr></tbody><tr><th>" + numberQuery + "</th><th>" +  uniqueIP.size() +"</th><th>" + numberActive)
		.concat("</th><th>"+  lastIP +"</th><th>"+  lastDate + "</th></tr></table>")
		.concat("<table border = 1><tbody><tr><th>URL</th><th>CountURL</th></tr></tbody><tr>");
		for(Entry<String, Integer> k : countURL.entrySet()) {
			result += "<tr><th>" + k.getKey() + "</th>" +
					  "<th>" + k.getValue() + "</th></tr>";
		}
		result.concat("</table>");
		result += ("<table border = 1><tbody><tr><th>IP</th><th>URI</th><th>Timestamp</th><th>Sent bytes</th><th>Recieved bytes</th>")
		.concat("<th>Speed(bytes/sec)</th></tr></tbody>");
		for(CollectionIP cip: listIP) {
				result +="<tr><th>" + cip.getIP() + "</th>" +
							 "<th>" + cip.getURL() + "</th>" +
					     	 "<th>" + cip.getDate()+ "</th>" +
						     "<th>" + cip.getSentBytes() + "</th>" +
						     "<th>" + cip.getRecieviedBytes() + "</th>" +
						     "<th>" + cip.getSpeed() + "</th></tr>";
		}
			result.concat("</tbody></table></html>");
		return result;
	}
	
	public synchronized void addConnection(CollectionIP cip) {
		if(listIP.size() > 15)
			listIP.remove(0);
			listIP.add(cip);
	}
	
	public synchronized void setCount() {
		numberQuery++;
	}
	
	public synchronized void setFirstIP(String ip) {
		firstIP = ip;
	}
	
	public synchronized String getFirstIP() {
		return firstIP;
	}
	
	public synchronized void putURLandCountHim(String url) {
		if(countURL.containsKey(url)) {
			countURL.put(url, new Integer(countURL.get(url)+1));
		} else {
			countURL.put(url, new Integer(1));
		}
	}
	
	/*
	 * Unique connection are considered by the unique query
	 */
	public synchronized void setCountUniqueConnection(String s) {
		if(!s.equals("/favicon.ico"))
			uniqueIP.add(s);
	}
	
	public synchronized void setNumberAcvite() {
		numberActive++;
	}
	
	public synchronized void dropNumberActive() {
		numberActive--;
	}

	
}
