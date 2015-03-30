package com.mitya;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        speed = System.currentTimeMillis();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        recievedBytes += msg.toString().length();
        if (!(msg instanceof HttpRequest)) {
            return;
        }

        uri = ((HttpRequest) msg).getUri();
        // FullHttpResponse response = new ServerRequestHandler().checkValue((uri));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        speed = (System.currentTimeMillis() - speed) / 1000;
        speed = (recievedBytes + sentBytes) / speed;
        speed = new BigDecimal(speed).setScale(2, RoundingMode.UP).doubleValue();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
