package cn.sangedon.minicat.server.mapper;

import lombok.Data;

/**
 * @author dongliangqiong 2021-09-24 16:48
 */
@Data
public class MapperElement<T> {
    public String name = "";

    public T obj;
}
