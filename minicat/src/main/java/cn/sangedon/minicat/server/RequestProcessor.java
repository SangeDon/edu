package cn.sangedon.minicat.server;

import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

/**
 * @author dongliangqiong 2021-09-22 21:11
 */
public class RequestProcessor extends Thread {
    private Socket socket;

    private Map<String, HttpServlet> map;

    public RequestProcessor(Socket socket, Map<String, HttpServlet> map) {
        this.socket = socket;
        this.map = map;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            Request request = new Request(is);
            Response response = new Response(socket.getOutputStream());

            if (map.get(request.getUrl()) == null) {
                response.outputHtml(request.getUrl());
            } else {
                HttpServlet servlet = map.get(request.getUrl());
                servlet.service(request, response);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
