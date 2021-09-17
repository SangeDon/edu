package cn.sangedon.minicat.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * minicat 启动入口
 * @author dongliangqiong 2021-09-17 21:43
 */
public class BootStrap {

    private int port = 8080;

    public static void main(String[] args) throws IOException {
        BootStrap bootstrap = new BootStrap();
        bootstrap.init();
    }

    /**
     * Minicat 启动初始化的操作
     */
    public void init() throws IOException {
        ServerSocket ss = new ServerSocket(port);
        System.out.println("Minicat start on port: " + port);

        while (true) {
            Socket socket = ss.accept();
            InputStream is = socket.getInputStream();
            Request request = new Request(is);
            Response response = new Response(socket.getOutputStream());

            response.outputHtml(request.getUrl());
            socket.close();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
