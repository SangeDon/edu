package cn.sangedon.rpcdemo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author dongliangqiong 2021-10-13 14:57
 */
public class SelectorServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(9999));
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器创建成功");
        while (true) {
            int select = selector.select(2000);
            if (select == 0) {
                System.out.println("没有事件发生");
                continue;
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    SocketChannel accept = socketChannel.accept();
                    System.out.println("有客户端连接");
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int read = channel.read(allocate);
                    if (read > 0) {
                        System.out.println(new String(allocate.array(), 0, read, StandardCharsets.UTF_8));
                        channel.write(ByteBuffer.wrap("没钱".getBytes(StandardCharsets.UTF_8)));
                        channel.close();
                    }
                }
                iterator.remove();
            }
        }
    }
}
