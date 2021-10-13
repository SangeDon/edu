package cn.sangedon.rpcdemo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author dongliangqiong 2021-10-13 14:23
 */
public class ClientChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        socketChannel.write(ByteBuffer.wrap("老板，还钱".getBytes(StandardCharsets.UTF_8)));

        ByteBuffer allocate = ByteBuffer.allocate(1024);
        int read = socketChannel.read(allocate);
        System.out.println(new String(allocate.array(), 0, read, StandardCharsets.UTF_8));

        socketChannel.close();
    }
}
