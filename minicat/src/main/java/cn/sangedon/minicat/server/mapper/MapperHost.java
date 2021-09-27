package cn.sangedon.minicat.server.mapper;

import cn.sangedon.minicat.server.element.Host;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 17:13
 */
@Data
public class MapperHost extends MapperElement<Host> {
    private Map<String, MapperContext> contextMap = new HashMap<>();

    public MapperHost(String name, Host obj) {
        this.name = name;
        this.obj = obj;
    }

    public MapperHost() {
    }
}
