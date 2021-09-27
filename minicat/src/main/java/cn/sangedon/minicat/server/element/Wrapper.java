package cn.sangedon.minicat.server.element;

import cn.sangedon.minicat.server.servlet.HttpServlet;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 17:00
 */
@AllArgsConstructor
@Data
public class Wrapper {
    private String url;

    private HttpServlet httpServlet;
}
