package com.bxj;

import com.bxj.core.ChatMessageDecoder;
import com.bxj.core.ChatMessageEncoder;
import com.bxj.handler.ServerChannelHandler;
import com.bxj.handler.GateWayHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServer {
    NioEventLoopGroup boss = new NioEventLoopGroup();
    NioEventLoopGroup work = new NioEventLoopGroup();
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    public void start() throws Exception {
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioServerSocketChannel) throws Exception {
                        pipeline(nioServerSocketChannel.pipeline());
                    }
                });
        ChannelFuture sync = serverBootstrap.bind(8081).sync();
        sync.channel().closeFuture().sync();
    }

    private void pipeline(ChannelPipeline pipeline){
        pipeline.addLast(new ChatMessageEncoder());
        pipeline.addLast(new ChatMessageDecoder());
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new GateWayHandler());
        pipeline.addLast(new ServerChannelHandler());
    }

    public static void main(String[] args) throws Exception {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
