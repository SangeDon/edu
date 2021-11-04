package com.haizhi.kafkaimportdata.thirddata.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by chenbo on 2017/10/13.
 */
@Slf4j
public class BeanUtil {

    private static ObjectMapper objectMapper = ObjectMapperFactory.get();

    public static Object deepClone(Object obj) {
        try {// 将对象写到流里
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);// 从流里读出来
            ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
            ObjectInputStream oi = new ObjectInputStream(bi);
            return (oi.readObject());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static Object mapToBean(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return obj;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            log.error("transMap2Bean Error " + e);
        }
        return obj;
    }

    public static Map<String, Object> beanToMap(Object obj) throws IOException {
        if (obj == null) {
            return null;
        }
        String s = objectMapper.writeValueAsString(obj);
        return objectMapper.readValue(s, Map.class);
    }

    public static List<Map<String, Object>> ListBeanToMap(List<Object> objectList) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Object obj : objectList) {
            list.add(beanToMap(obj));
        }
        return list;
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws IOException {
        if (map == null) {
            return null;
        }
        String s = objectMapper.writeValueAsString(map);
        return objectMapper.readValue(s, clazz);
    }

    public static <T> T copyProperties(Object source, Class<T> clazz) throws Exception {
        if (source == null) {
            return null;
        }
        T t = clazz.newInstance();
        org.springframework.beans.BeanUtils.copyProperties(source, t);
        return t;
    }

    public static <T> List<T> copyListProperties(List source, Class<T> clazz) throws Exception {
        if (source == null) {
            return Lists.newLinkedList();
        }
        LinkedList<T> res = new LinkedList<>();
        for (Object o : source) {
            if (o == null) {
                continue;
            }
            T t = clazz.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(o, t);
            res.add(t);
        }
        return res;
    }
}
