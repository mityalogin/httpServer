package com.mitya;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class InitializerServer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel c) throws Exception {
        String ip = c.remoteAddress().getHostString();
        c.pipeline().addLast(new ServerHandler(ip));
    }

}
