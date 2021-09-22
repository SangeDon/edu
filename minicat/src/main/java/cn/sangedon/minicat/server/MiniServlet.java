package cn.sangedon.minicat.server;

/**
 * @author dongliangqiong 2021-09-22 20:17
 */
public interface MiniServlet {
    void init() throws Exception;

    void destroy() throws Exception;

    void service(Request request, Response response) throws Exception;
}
