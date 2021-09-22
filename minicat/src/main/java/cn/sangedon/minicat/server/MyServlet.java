package cn.sangedon.minicat.server;

/**
 * @author dongliangqiong 2021-09-22 20:22
 */
public class MyServlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        String content = "<h1>Hello MyServlet GET!</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>Hello MyServlet!</h1>";
        try {
            response.output(HttpProtocolUtil.getHttpHeader200(content.getBytes().length) + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void destroy() throws Exception {

    }
}
