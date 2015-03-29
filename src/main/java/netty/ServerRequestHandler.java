package netty;

/**
 * @author Pogorelov
 *
 */

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;


import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class ServerRequestHandler {
	//The final variable DELAY to set the time delay
	private static final int DELAY = 10;
	
	
	public FullHttpResponse checkValue(String value) throws InterruptedException {
		//The variable for URL, but little case cause not final
		String url = "";
		
		//This block 'if' for check URI
		if(value.contains("%3C")) {
			//In any case we always take full URL address
			url = value.substring(17, value.length()-3);
			value = value.substring(0, 9);
			RequestStatistics.getInstance().putURLandCountHim(url);
		}
		

		
		/*
		 * The switch for selecting the correct query.
		 * Example: redirect?url=<http://google.com/> 
		 */
		switch(value) {
			case "/hello":
				return valueHelloWorld();
			case "/status":
				return valueStatus();
			case "/redirect":
				return valueRedirect(url);							   	
			default:
				return notFoundValue();
		}
	}
	
	//The method which provides an answer to the Hello world query
	private FullHttpResponse valueHelloWorld() throws InterruptedException {
		String hello = "<head><font size=\"5\">Hello world!</font></head>";
		FullHttpResponse value = new DefaultFullHttpResponse(HTTP_1_1, OK,
				Unpooled.copiedBuffer(hello, CharsetUtil.US_ASCII));
		Thread.sleep(DELAY * 1000);
		          System.out.println(value);
		return value;
	}
	
	//The method which provides an answer to the Redirect query
	private FullHttpResponse valueRedirect(String url) {
		
		FullHttpResponse value = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FOUND);
		value.headers().set(HttpHeaders.Names.LOCATION, url);
		
		return value;
	}
	
	//The method which provides an answer to the Status query
	private FullHttpResponse valueStatus() {
		
		FullHttpResponse value = new DefaultFullHttpResponse(HTTP_1_1, OK,
				Unpooled.copiedBuffer(ServerInitializer.rs.getReport(), CharsetUtil.US_ASCII));
		
		return value;
	}
	
	//The method which provides an answer to the not found page query
	private FullHttpResponse notFoundValue() {
		String notFound = "<head><tr>Sorry, the page is not available <p> 404 Not Found</tr></head>";
		DefaultFullHttpResponse value = new DefaultFullHttpResponse(HTTP_1_1, OK, 
				Unpooled.copiedBuffer(notFound, CharsetUtil.US_ASCII));
		
		return value;
	}

}
