package cn.sangedon.rpc.chat.netty;

import cn.sangedon.rpc.chat.config.NettyConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author dongliangqiong 2021-10-20 20:52
 * @Description TODO
 */
@Component
public class WebSocketChannelInit extends ChannelInitializer {
    @Autowired
    private NettyConfig nettyConfig;

    @Autowired
    private WebSockethandler webSockethandler;

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());

        // 支持大数据
        pipeline.addLast(new ChunkedWriteHandler());

        pipeline.addLast(new HttpObjectAggregator(8000));

        // 支持websocket协议
        pipeline.addLast(new WebSocketServerProtocolHandler(nettyConfig.getPath()));

        // 添加自定义创建的handler
        pipeline.addLast(new WebSocketServerProtocolHandler(nettyConfig.getPath()));
        pipeline.addLast(webSockethandler);
    }
}
