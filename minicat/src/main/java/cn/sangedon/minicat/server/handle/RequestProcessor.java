package cn.sangedon.minicat.server.handle;

import cn.sangedon.minicat.server.element.Wrapper;
import cn.sangedon.minicat.server.mapper.MapperContext;
import cn.sangedon.minicat.server.mapper.MapperHost;
import cn.sangedon.minicat.server.mapper.MapperWrapper;
import cn.sangedon.minicat.server.util.HttpProtocolUtil;
import java.io.File;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author dongliangqiong 2021-09-22 21:11
 */
public class RequestProcessor extends Thread {
    private Socket socket;

    private MapperHost mapperHost;

    public RequestProcessor(Socket socket, MapperHost mapperHost) {
        this.socket = socket;
        this.mapperHost = mapperHost;
    }

    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            Request request = new Request(is);
            Response response = new Response(socket.getOutputStream());
            String url = request.getUrl();

            String[] paths = url.split("\\/");
            String context = paths[1];
            MapperContext mapperContext = mapperHost.getContextMap().get(context);
            if (mapperContext == null) {
                response.output(HttpProtocolUtil.getHttpHeader404());
                return;
            }

            String servlet = url.substring(url.lastIndexOf(context) + context.length());
            MapperWrapper mapperWrapper = mapperContext.getWrapperMap().get(servlet);
            if (mapperWrapper == null) {
                response.outputHtml(mapperHost.getObj().getAppBase() + File.separator + context + servlet, true);
                return;
            }

            Wrapper wrapper = mapperWrapper.getObj();
            wrapper.getHttpServlet().service(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
