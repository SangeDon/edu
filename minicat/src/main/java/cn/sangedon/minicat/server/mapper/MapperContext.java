package cn.sangedon.minicat.server.mapper;

import cn.sangedon.minicat.server.element.Context;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 16:50
 */
@Data
public class MapperContext extends MapperElement<Context> {
    private Map<String, MapperWrapper> wrapperMap = new HashMap<>();

    public MapperContext(String name, Context obj) {
        this.name = name;
        this.obj = obj;
    }

    public MapperContext() {
    }
}
