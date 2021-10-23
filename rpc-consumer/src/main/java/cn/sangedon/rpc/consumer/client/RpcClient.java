package cn.sangedon.rpc.consumer.client;

import cn.sangedon.rpc.consumer.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-23 14:24
 */
public class RpcClient {
    private EventLoopGroup group = new NioEventLoopGroup();

    private Channel channel;

    private String ip;

    private int port;

    private RpcClientHandler rpcClientHandler = new RpcClientHandler();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        initClient();
    }

    public void initClient() {
        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                     .channel(NioSocketChannel.class)
                     .option(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                     .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                     .handler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel channel) throws Exception {
                             ChannelPipeline pipeline = channel.pipeline();
                             pipeline.addLast(new StringDecoder());
                             pipeline.addLast(new StringEncoder());
                             pipeline.addLast(rpcClientHandler);
                         }
                     });

            channel = bootstrap.connect(ip, port).sync().channel();
        } catch (Exception e) {
            if (channel != null) {
                channel.close();
            }

            if (group != null) {
                group.shutdownGracefully();
            }
        }
    }

    public void close() {
        if (channel != null) {
            channel.close();
        }

        if (group != null) {
            group.shutdownGracefully();
        }
    }

    public Object send(String msg) throws ExecutionException, InterruptedException {
        rpcClientHandler.setRequestMsg(msg);
        Future submit = executorService.submit(rpcClientHandler);
        return submit.get();
    }
}
