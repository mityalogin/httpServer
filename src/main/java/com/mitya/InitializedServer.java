package com.mitya;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class InitializedServer extends ChannelInitializer<SocketChannel> {

   // public static RequestStatistics rs = RequestStatistics.getInstance();
    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        String ip = c.remoteAddress().getHostString();
        ChannelPipeline pipeline = c.pipeline();
		
		pipeline.addLast("decoder", new HttpRequestDecoder());
	    pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("handler", new ServerHandler(ip));
    }

}
