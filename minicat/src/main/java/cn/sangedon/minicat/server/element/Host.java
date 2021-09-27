package cn.sangedon.minicat.server.element;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 16:58
 */
@AllArgsConstructor
@Data
public class Host {
    private String hostName;

    private String appBase;
}
