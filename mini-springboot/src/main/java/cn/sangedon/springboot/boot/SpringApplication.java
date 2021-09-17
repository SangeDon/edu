package cn.sangedon.springboot.boot;

/**
 * @author dongliangqiong 2021-09-08 18:28
 */
public class SpringApplication {

    public static void run(Class<?> primarySource, String... args) {
        WebServer webServer = new TomcatWebServer();
        webServer.start();
    }
}
