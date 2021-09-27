package cn.sangedon.minicat.server.mapper;

import cn.sangedon.minicat.server.element.Wrapper;
import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 17:15
 */
@Data
public class MapperWrapper extends MapperElement<Wrapper> {
    public MapperWrapper(String name, Wrapper obj) {
        this.name = name;
        this.obj = obj;
    }

    public MapperWrapper() {
    }
}
