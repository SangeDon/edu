package cn.sangedon.minicat.server.startup;

import cn.sangedon.minicat.server.element.Context;
import cn.sangedon.minicat.server.element.Host;
import cn.sangedon.minicat.server.element.Wrapper;
import cn.sangedon.minicat.server.handle.RequestProcessor;
import cn.sangedon.minicat.server.mapper.MapperContext;
import cn.sangedon.minicat.server.mapper.MapperHost;
import cn.sangedon.minicat.server.mapper.MapperWrapper;
import cn.sangedon.minicat.server.servlet.HttpServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private static final String SERVER_NAME = "server.xml";

    private static final String WEB_NAME = "web.xml";

    private int port = 8080;

    private Map<String, HttpServlet> map = new HashMap<>();

    private MapperHost mapperHost = new MapperHost();

    public static void main(String[] args) throws Exception {
        BootStrap bootstrap = new BootStrap();
        bootstrap.init();
    }

    public void init() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        loadServer();

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        AbortPolicy handle = new AbortPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 50, 100L, TimeUnit.SECONDS, workQueue, threadFactory,
                                                                       handle);
        while (true) {
            Socket socket = serverSocket.accept();
            RequestProcessor requestProcessor = new RequestProcessor(socket, mapperHost);
            threadPoolExecutor.execute(requestProcessor);
        }
    }

    private void loadServer() {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(SERVER_NAME)) {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element rootElement = doc.getRootElement();
            Element connector = (Element) rootElement.selectSingleNode("//Connector");
            this.port = Integer.parseInt(Optional.ofNullable(connector.attributeValue("port")).orElseGet(() -> "8080"));
            Element engine = (Element) rootElement.selectSingleNode("//Engine");
            List<Element> hostNodes = engine.selectNodes("Host");
            for (int i = 0; i < hostNodes.size(); i++) {
                Element element = hostNodes.get(i);
                mapperHost.setObj(new Host(Optional.ofNullable(element.attributeValue("name")).orElseGet(() -> "localhost"),
                                           element.attributeValue("appBase")));
            }
            String appBase = mapperHost.getObj().getAppBase();
            File file = new File(appBase);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (!f.exists() || !f.isDirectory()) {
                    continue;
                }
                String name = f.getName();
                Context context = new Context(name);
                MapperContext mapperContext = new MapperContext(name, context);
                Optional.ofNullable(mapperHost.getContextMap()).orElseGet(() -> new HashMap<>()).put(name, mapperContext);
                loadConfig(appBase, mapperContext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfig(String appBase, MapperContext mapperContext) {
        File file = new File(appBase + File.separator + mapperContext.getName() + File.separator + WEB_NAME);
        File appFile = new File(appBase + File.separator + mapperContext.getName());
        try (FileInputStream fis = new FileInputStream(file)) {
            URL url = appFile.toURI().toURL();
            URL[] urls = {url};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(fis);
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
                final Wrapper wrapper = new Wrapper(urlPattern, (HttpServlet) urlClassLoader.loadClass(servletClass).newInstance());
                final MapperWrapper mapperWrapper = new MapperWrapper(urlPattern, wrapper);
                Optional.ofNullable(mapperContext.getWrapperMap()).orElseGet(() -> new HashMap<>()).put(urlPattern, mapperWrapper);
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
