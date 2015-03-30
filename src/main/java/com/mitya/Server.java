package com.mitya;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

    public final int port;

    Server(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class).childHandler(new InitializedServer());

            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
             System.err.println("Error starting");
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
