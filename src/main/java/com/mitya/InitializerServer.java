package com.mitya;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class InitializerServer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        String ip = c.remoteAddress().getHostString();
        c.pipeline().addLast("decoder", new HttpRequestDecoder());
        c.pipeline().addLast("encoder", new HttpResponseEncoder());
        c.pipeline().addLast("handler", new ServerHandler());
    }

}
