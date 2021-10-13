package cn.sangedon.rpcdemo.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dongliangqiong 2021-10-13 09:37
 */
public class ServerDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handle(socket);
                }
            });
        }
    }

    private static void handle(Socket socket) {
        try {
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = is.read(bytes);
            System.out.println(new String(bytes, 0, read).trim());
            Scanner scanner = new Scanner(System.in);
            String next = scanner.nextLine();
            OutputStream os = socket.getOutputStream();
            os.write(next.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
