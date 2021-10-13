package cn.sangedon.rpcdemo.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author dongliangqiong 2021-10-13 09:37
 */
public class ClientDemo {

    public static void main(String[] args) throws Exception {
        while (true) {
            Socket socket = new Socket("127.0.0.1", 9999);
            OutputStream os = socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            String next = scanner.nextLine();
            os.write(next.getBytes());

            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = is.read(bytes);
            System.out.println(new String(bytes, 0, read).trim());
            socket.close();
        }
    }
}
