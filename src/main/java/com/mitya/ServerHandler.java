package com.mitya;

import static com.mitya.InitializerServer.sample;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private String ip;
    private String uri;
    private int sent_Bytes;
    private int recieved_Bytes;
    private double speed;

    public ServerHandler(String ip) {
        this.ip = ip;
        sample.newIP(ip);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        speed = System.currentTimeMillis();
        if (!(msg instanceof HttpRequest)) {
            return;
        }
        uri = ((HttpRequest) msg).getUri();
        recieved_Bytes = msg.toString().length();
        sample.increaseNumber();
        sample.increaseActive();
        FullHttpResponse page = new ServerRequestHandler().checkValue((uri));
        ctx.write(page).addListener(ChannelFutureListener.CLOSE);
        sent_Bytes = page.content().writerIndex();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        sample.reduceActive();
        ctx.flush();

        speed = (int) ((sent_Bytes + recieved_Bytes) / ((System.currentTimeMillis() - speed) / 1000)) * 1000;

        Data value = new Data(ip, uri, sent_Bytes, recieved_Bytes, speed / 1000);
        sample.addConn(value);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
