package cn.sangedon.minicat.server.servlet;

import cn.sangedon.minicat.server.handle.Request;
import cn.sangedon.minicat.server.handle.Response;

/**
 * @author dongliangqiong 2021-09-22 20:18
 */
public abstract class HttpServlet implements MiniServlet {
    public abstract void doGet(Request request, Response response);

    public abstract void doPost(Request request, Response response);

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void service(Request request, Response response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
