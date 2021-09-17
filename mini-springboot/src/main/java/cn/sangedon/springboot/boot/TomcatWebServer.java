package cn.sangedon.springboot.boot;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.catalina.startup.Tomcat;

/**
 * tomcat服务器
 * @author dongliangqiong 2021-09-08 18:37
 */
public class TomcatWebServer implements WebServer {
    private final Tomcat tomcat;

    private ReentrantLock lock = new ReentrantLock();

    private AtomicBoolean started = new AtomicBoolean(false);

    public TomcatWebServer() {
        this.tomcat = new Tomcat();
    }

    @Override
    public void start() {
        lock.lock();
        try {
            if (started.get()) {
                return;
            }
            tomcat.setPort(8080);
            tomcat.addWebapp("/", "/");
            tomcat.start();
            started.set(true);
            tomcat.getServer().await();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void getPort() {

    }
}
