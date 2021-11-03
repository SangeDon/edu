package cn.sangedon.rpc.provider.server;

import cn.sangedon.rpc.provider.config.RpcServerConfig;
import cn.sangedon.rpc.provider.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dongliangqiong 2021-10-23 11:56
 * @Description Rpc 服务
 */
@Service
public class RpcServer implements DisposableBean {
    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    @Autowired
    private RpcServerHandler rpcServerHandler;

    @Autowired
    private RpcServerConfig rpcServerConfig;

    @Autowired
    private ZkClient zkClient;

    public void startServer() {
        startServer(rpcServerConfig.getHost(), rpcServerConfig.getPort());
    }

    public void startServer(String ip, Integer port) {
        try {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                     .channel(NioServerSocketChannel.class)
                     .childHandler(new ChannelInitializer<SocketChannel>() {
                         @Override
                         protected void initChannel(SocketChannel channel) throws Exception {
                             ChannelPipeline pipeline = channel.pipeline();
                             pipeline.addLast(new StringDecoder());
                             pipeline.addLast(new StringEncoder());
                             // 业务处理类
                             pipeline.addLast(rpcServerHandler);
                         }
                     });

            ChannelFuture channelFuture = bootstrap.bind(ip, port).sync();
            channelFuture.channel().closeFuture().sync();
            zkClient.createEphemeral(ZkServer.BASE_PATH + ip + "/" + port);
            System.out.println("服务启动成功");
        } catch (Exception e) {
            e.printStackTrace();
            zkClient.deleteRecursive(ZkServer.BASE_PATH + ip + "/" + port);
        } finally {
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }

            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        zkClient.deleteRecursive(ZkServer.BASE_PATH + rpcServerConfig.getHost() + "/" + rpcServerConfig.getPort());
    }
}
