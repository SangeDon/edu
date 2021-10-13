package cn.sangedon.rpcdemo.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author dongliangqiong 2021-10-13 14:23
 */
public class ServerSocketChannelDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务端启动成功");
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                System.out.println("没有客户端连接");
                Thread.sleep(1000);
                continue;
            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            System.out.println(new String(buffer.array(), 0, read, StandardCharsets.UTF_8));

            socketChannel.write(ByteBuffer.wrap("sange".getBytes(StandardCharsets.UTF_8)));

            socketChannel.close();
        }
    }
}
