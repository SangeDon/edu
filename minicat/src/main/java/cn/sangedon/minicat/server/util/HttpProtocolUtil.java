package cn.sangedon.minicat.server.util;

/**
 * http协议工具类，主要提供响应头信息
 * @author dongliangqiong 2021-09-17 22:02
 */
public class HttpProtocolUtil {
    public static String getHttpHeader200(long contentLength) {
        return "HTTP/1.1 200 OK \n" +
               "Content-Type: text/html; charset=UTF-8 \n" +
               "Content-Length: " + contentLength + " \n" +
               "\r\n";
    }

    public static String getHttpHeader404() {
        String str404 = "<h1>404 NOT FOUND</h1>";
        return "HTTP/1.1 NOT FOUND \n" +
               "Content-Type: text/html \n" +
               "Content-Length: " + str404.getBytes().length + " \n" +
               "\r\n" + str404;
    }
}
