package cn.sangedon.minicat.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 封装Response对象
 * @author dongliangqiong 2021-09-17 22:44
 */
public class Response {
    /**
     * 响应对象输出流
     */
    private OutputStream outputStream;

    public Response() {
    }

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * 获取制定路径静态资源
     * @param path
     */
    public void outputHtml(String path) throws IOException {
        // 获取静态资源路径
        String absoluteResourcePath = StaticResouceUtil.getAbsolutrPath(path);

        // 输入静态资源路径
        File file = new File(absoluteResourcePath);
        if (file.exists()) {
            // 输出静态资源
            StaticResouceUtil.outputStaticResource(new FileInputStream(file), outputStream);
        } else {
            // 返回404
            this.output(HttpProtocolUtil.getHttpHeader404());
        }
    }

    public void output(String content) throws IOException {
        outputStream.write(content.getBytes());
    }
}
