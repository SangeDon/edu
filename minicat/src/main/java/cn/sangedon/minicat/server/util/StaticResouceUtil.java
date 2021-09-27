package cn.sangedon.minicat.server.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 静态资源工具类
 * @author dongliangqiong 2021-09-17 22:55
 */
public class StaticResouceUtil {
    /**
     * 获取静态资源绝对路径
     * @param path
     * @return
     */
    public static String getAbsolutrPath(String path) {
        String absolutePath = StaticResouceUtil.class.getResource("/").getPath();
        return absolutePath.replaceAll("\\\\", File.separator) + path;
    }

    public static void outputStaticResource(InputStream is, OutputStream os) throws IOException {
        int count = 0;
        while (count == 0) {
            count = is.available();
        }
        int resourceSize = count;
        os.write(HttpProtocolUtil.getHttpHeader200(resourceSize).getBytes());

        long written = 0;
        int byteSize = 1024;
        byte[] bytes = new byte[byteSize];
        while (written < resourceSize) {
            if ((written + byteSize) > resourceSize) {
                byteSize = (int) (resourceSize - written);
                bytes = new byte[byteSize];
            }
            is.read(bytes);
            os.write(bytes);
            os.flush();
            written += byteSize;
        }
    }
}
