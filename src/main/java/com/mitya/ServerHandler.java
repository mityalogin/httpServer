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
    private double send_speed;
    private double recieved_speed;

    public ServerHandler(String ip) {
        this.ip = ip;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        send_speed=System.currentTimeMillis();
        if (!(msg instanceof HttpRequest)) {
            return;
        }
        uri = ((HttpRequest) msg).getUri();
        recieved_Bytes=msg.toString().length();
        sample.increaseNumber();
        sample.increaseActive();
        recieved_speed=System.currentTimeMillis();
        FullHttpResponse page = new ServerRequestHandler().checkValue((uri));
        ctx.write(page).addListener(ChannelFutureListener.CLOSE);
        sent_Bytes = page.content().writerIndex();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        sample.reduceActive();
        ctx.flush();
        send_speed=(int)(sent_Bytes/((System.currentTimeMillis()-send_speed)/1000))*1000;
        recieved_speed=(int)(recieved_Bytes/((System.currentTimeMillis()-recieved_speed)/1000))*1000;
        Data value =new Data(ip,uri,sent_Bytes, recieved_Bytes, send_speed/1000, recieved_speed/1000);
        sample.addConn(value);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
