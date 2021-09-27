package cn.sangedon.minicat.server.handle;

import java.io.IOException;
import java.io.InputStream;

/**
 * 封装请求信息
 * @author dongliangqiong 2021-09-17 22:35
 */
public class Request {
    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求路径
     */
    private String url;

    private InputStream inputStream;

    public Request() {
    }

    public Request(InputStream inputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] bytes = new byte[count];
        inputStream.read(bytes);
        String inputStr = new String(bytes);
        String firstLine = inputStr.split("\\n")[0];
        String[] strs = firstLine.split(" ");
        this.method = strs[0];
        this.url = strs[1];
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
