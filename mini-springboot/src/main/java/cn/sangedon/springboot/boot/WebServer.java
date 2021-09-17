package cn.sangedon.springboot.boot;

/**
 * web服务器
 * @author dongliangqiong 2021-09-08 18:34
 */
public interface WebServer {
    /**
     * 启动服务
     */
    void start();

    /**
     * 关闭服务
     */
    void stop();

    /**
     * 获取服务端口
     */
    void getPort();
}
