package cn.sangedon.springboot.boot;

import cn.sangedon.springboot.mvc.annotation.Autowired;
import cn.sangedon.springboot.mvc.annotation.ComponentScan;
import cn.sangedon.springboot.mvc.annotation.Controller;
import cn.sangedon.springboot.mvc.annotation.Service;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Data;

/**
 * @author dongliangqiong 2021-09-09 11:48
 */
@Data
public class ApplicationContext {
    private List<String> classNameList = new ArrayList<>();

    private Map<String, Object> IocMap = new HashMap<>();

    private String basePackage = "";

    public void register(Class<AppConfig> appConfigClass) {
        ComponentScan componentScan = appConfigClass.getAnnotation(ComponentScan.class);
        if (componentScan != null) {
            basePackage = componentScan.value();
        }
    }

    public void refresh() {
        // 扫描注解
        doScan(basePackage);

        // 实例化
        doInstance();

        // 依赖注入
        doAutowred();
    }

    private void doScan(String scanPackage) {
        String scanpath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "\\/");
        File pack = new File(scanpath);
        File[] files = pack.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                classNameList.add(className);
            }
        }
    }

    private void doInstance() {
        if (classNameList.size() == 0) {
            return;
        }

        try {
            for (String className : classNameList) {
                Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(Controller.class)) {
                    String simpleName = aClass.getSimpleName();
                    String lowerFirst = lowerFirst(simpleName);
                    Object instance = aClass.newInstance();
                    IocMap.put(lowerFirst, instance);
                } else if (aClass.isAnnotationPresent(Service.class)) {
                    Service service = aClass.getAnnotation(Service.class);
                    String beanName = service.value();
                    Object instance = aClass.newInstance();
                    if (beanName != null || beanName != "") {
                        IocMap.put(beanName, instance);
                    } else {
                        String simpleName = aClass.getSimpleName();
                        String lowerFirst = lowerFirst(simpleName);
                        IocMap.put(lowerFirst, instance);
                    }

                    Class<?>[] interfaces = aClass.getInterfaces();
                    for (Class<?> anInterface : interfaces) {
                        String simpleName = anInterface.getSimpleName();
                        IocMap.put(lowerFirst(simpleName), instance);
                    }
                } else {
                    continue;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doAutowred() {
        try {
            if (IocMap == null || IocMap.size() == 0) {
                return;
            }
            for (Entry<String, Object> objectEntry : IocMap.entrySet()) {
                Object value = objectEntry.getValue();
                Field[] fields = value.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Autowired.class)) {
                        continue;
                    }
                    String autowiredValue = field.getAnnotation(Autowired.class).value();
                    field.setAccessible(true);
                    if (autowiredValue == null || autowiredValue == "") {
                        String simpleName = field.getClass().getSimpleName();
                        field.set(value, IocMap.get(lowerFirst(simpleName)));
                    } else {
                        field.set(value, IocMap.get(autowiredValue));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String lowerFirst(String name) {
        if (name == null || name == "") {
            return name;
        }
        char[] chars = name.toCharArray();
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            chars[0] += 32;
        }
        return String.valueOf(chars);
    }
}
