package cn.sangedon.rpcdemo.io.nio;

import java.nio.ByteBuffer;

/**
 * NIO buffer API测试
 * @author dongliangqiong 2021-10-13 11:35
 */
public class CreateBufferDemo {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("0123".getBytes());
        System.out.println(buffer.position());

        buffer.flip();
        System.out.println(buffer.limit());
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.get());
        }
        buffer.rewind();
        byte[] bytes = new byte[4];
        buffer.get(bytes);
        System.out.println(new String(bytes));

        buffer.clear();

        buffer.put("sange".getBytes());
        buffer.flip();
        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.get());
        }
        buffer.rewind();
        byte[] bytes1 = new byte[5];
        buffer.get(bytes1);
        System.out.println(new String(bytes1));
    }
}
