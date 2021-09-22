package cn.sangedon.minicat.server;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * minicat 启动入口
 * @author dongliangqiong 2021-09-17 21:43
 */
public class BootStrap {

    private int port = 8080;

    private Map<String, HttpServlet> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BootStrap bootstrap = new BootStrap();
        bootstrap.init();
    }

    /**
     * Minicat 启动初始化的操作
     */
    /*public void init() throws IOException {
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
    }*/
    /*public void init() throws Exception {
        loadConfig();

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            Socket socker = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socker, map);
            requestProcessor.start();
        }
    }*/
    public void init() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        loadConfig();

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        AbortPolicy handle = new AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 50, 100L, TimeUnit.SECONDS, workQueue, threadFactory,
                                                                       handle);
        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, map);
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    private void loadConfig() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("web.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document doc = saxReader.read(is);
            Element rootElement = doc.getRootElement();

            List<Element> selectNodes = rootElement.selectNodes("//servlet");
            for (int i = 0; i < selectNodes.size(); i++) {
                Element element = selectNodes.get(i);
                Element servletNameElement = (Element) element.selectSingleNode("servlet-name");
                String servletName = servletNameElement.getStringValue();

                Element servletClassElement = (Element) element.selectSingleNode("servlet-class");
                String servletClass = servletClassElement.getStringValue();

                Element servletMapping = (Element) element
                    .selectSingleNode("/web-app/servlet-mapping[servlet-name='" + servletName + "']");
                String urlPattern = servletMapping.selectSingleNode("url-pattern").getStringValue();

                map.put(urlPattern, (HttpServlet) Class.forName(servletClass).newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
