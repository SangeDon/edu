package cn.sangedon.minicat.server.element;

import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 16:59
 */
@Data
public class Context {
    private String name;

    public Context(String name) {
        this.name = name;
    }
}
