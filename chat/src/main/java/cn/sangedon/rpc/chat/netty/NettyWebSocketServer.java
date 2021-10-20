package cn.sangedon.rpc.chat.netty;

import cn.sangedon.rpc.chat.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongliangqiong 2021-10-20 20:43
 * @Description TODO
 */
@Component
public class NettyWebSocketServer implements Runnable {
    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private WebSocketChannelInit webSocketChannelInit;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PreDestroy
    public void close() {
        closeServer();
    }

    @Override
    public void run() {
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(webSocketChannelInit);
            ChannelFuture channelFuture = bootstrap.bind(nettyConfig.getPort()).sync();
            System.out.println("服务端启动完成");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            closeServer();
        } finally {
            closeServer();
        }
    }

    private void closeServer() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
