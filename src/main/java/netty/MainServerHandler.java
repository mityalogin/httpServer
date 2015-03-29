package netty;

/**
 * @author Pogorelov
 *
 */

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainServerHandler extends ChannelInboundHandlerAdapter {
	//Some information
	private String uri;
	private String ip;
	private int sentBytes;
	private int recievedBytes;
	private double speed;
	
	MainServerHandler(String ip) {
		this.ip = ip;
	}

	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		//Take current time
		speed = System.currentTimeMillis();

		//Take the number of open connections
		ServerInitializer.rs.setCount();
		
		//Take the number of acvite connections
		ServerInitializer.rs.setNumberAcvite();
		
		//First IP for first request
		RequestStatistics.getInstance().setFirstIP(ip);
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		
		/*
		 * Take recievedBytes
		 */
		recievedBytes += msg.toString().length();
		
		if(!(msg instanceof HttpRequest)) 
			return;
		
		uri = ((HttpRequest) msg).getUri();
		FullHttpResponse response = new ServerRequestHandler().checkValue((uri));
		
		/*
		 * Take URL below
		 */
		if(uri.contains("%3C")) {
			this.uri = uri.substring(17, uri.length()-3);
		} 
		
		/*
		 * Take unique connection
		 */
		ServerInitializer.rs.setCountUniqueConnection(uri);
		
		if(response != null) {
			/*
			 * Take sentBytes
			 */
			this.sentBytes = response.content().writerIndex();
			
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * Take speed here
		 */
		speed = (System.currentTimeMillis() - speed) / 1000;
		speed = (recievedBytes + sentBytes) / speed;

		//Rounded to two digits
		speed = new BigDecimal(speed).setScale(2, RoundingMode.UP).doubleValue();

		//Transmitting data below
		CollectionIP cip = new CollectionIP(ip, uri, sentBytes, recievedBytes, speed);
		ServerInitializer.rs.addConnection(cip);
		
		//Drop the number of active connections
		ServerInitializer.rs.dropNumberActive();
		
		ctx.flush();
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
}
