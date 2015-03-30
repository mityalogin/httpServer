package com.mitya;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private String uri;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof HttpRequest)) {
            return;
        }
        uri = ((HttpRequest) msg).getUri();
        System.out.println(uri);
        FullHttpResponse page = new ServerRequestHandler().checkValue((uri));
        System.out.println("22");
        ctx.write(page).addListener(ChannelFutureListener.CLOSE);
        System.out.println("33");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
