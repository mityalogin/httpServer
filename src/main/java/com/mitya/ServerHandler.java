package com.mitya;

import static com.mitya.InitializerServer.sample;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private String uri;
    private String ip;

    public ServerHandler(String ip) {
        this.ip = ip;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof HttpRequest)) {
            return;
        }
        uri = ((HttpRequest) msg).getUri();
        sample.increaseNumber();
        sample.increaseActive();
        FullHttpResponse page = new ServerRequestHandler().checkValue((uri));
        ctx.write(page).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        sample.reduceActive();
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
