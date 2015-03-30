package com.mitya;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private String uri;
    private String ip;
    private int sentBytes;
    private int recievedBytes;
    private double speed;

    ServerHandler(String ip) {
        this.ip = ip;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        recievedBytes += msg.toString().length();
        if (!(msg instanceof HttpRequest)) {
            return;
        }
        uri = ((HttpRequest) msg).getUri();
        System.out.println(uri);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
