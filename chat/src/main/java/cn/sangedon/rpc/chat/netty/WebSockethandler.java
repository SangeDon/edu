package cn.sangedon.rpc.chat.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * 服务端处理器
 *
 * @author dongliangqiong 2021-10-20 20:57
 */
@Component
@Sharable
public class WebSockethandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private List<Channel> channelList = new ArrayList<>();

    /**
     * 通道就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.add(channel);
        System.out.println(channel.remoteAddress().toString().substring(1) + " : 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelList.remove(channel);
        System.out.println(channel.remoteAddress().toString().substring(1) + " : 下线");
    }

    /**
     * 读就绪事件
     *
     * @param ctx
     * @param textWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String text = textWebSocketFrame.text();
        System.out.println(text);
        Channel channel = ctx.channel();
        for (Channel c : channelList) {
            if (c != channel) {
                c.writeAndFlush(new TextWebSocketFrame(text));
            }
        }
    }
}
