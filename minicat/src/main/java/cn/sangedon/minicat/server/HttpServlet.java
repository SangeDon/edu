package cn.sangedon.minicat.server;

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
            Thread.sleep(5000);
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
