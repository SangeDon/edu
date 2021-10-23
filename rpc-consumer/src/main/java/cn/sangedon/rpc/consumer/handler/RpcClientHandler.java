package cn.sangedon.rpc.consumer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.Callable;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Rpc 客户端处理器
 *
 * @author dongliangqiong 2021-10-23 14:38
 */
@Component
@Data
public class RpcClientHandler extends SimpleChannelInboundHandler<String> implements Callable {
    private ChannelHandlerContext ctx;

    private String requestMsg;

    private String responseMsg;

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        responseMsg = s;
        notify();
    }

    /**
     * 完成消息的发送
     *
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        ctx.writeAndFlush(requestMsg);
        wait();
        return responseMsg;
    }

    /**
     * 通道连接就绪事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }
}
