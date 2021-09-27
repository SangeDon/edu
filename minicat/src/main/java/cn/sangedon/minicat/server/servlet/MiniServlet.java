package cn.sangedon.minicat.server.servlet;

import cn.sangedon.minicat.server.handle.Request;
import cn.sangedon.minicat.server.handle.Response;

/**
 * @author dongliangqiong 2021-09-22 20:17
 */
public interface MiniServlet {
    void init() throws Exception;

    void destroy() throws Exception;

    void service(Request request, Response response) throws Exception;
}
