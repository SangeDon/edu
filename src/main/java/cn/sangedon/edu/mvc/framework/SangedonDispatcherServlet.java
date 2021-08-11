package cn.sangedon.edu.mvc.framework;

import cn.sangedon.edu.mvc.framework.annotation.SangedonAutowired;
import cn.sangedon.edu.mvc.framework.annotation.SangedonController;
import cn.sangedon.edu.mvc.framework.annotation.SangedonRequestMapping;
import cn.sangedon.edu.mvc.framework.annotation.SangedonService;
import cn.sangedon.edu.mvc.framework.pojo.Handler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * mvc转发器
 * @author dongliangqiong
 */
public class SangedonDispatcherServlet extends HttpServlet {
    private Properties properties;

    private List<String> classNameList = new ArrayList<>();

    private Map<String, Object> IocMap = new HashMap<>();

    private Map<Pattern, Handler> handleMapping = new HashMap<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 读取配置
        String contextConfiguration = config.getInitParameter("contextConfigLocation");
        doLoadConfig(contextConfiguration);

        // 扫描注解
        doScan(properties.getProperty("scanPackage"));

        // 实例化
        doInstance();

        // 依赖注入
        doAutowred();

        // 建立映射
        initHandleMapping();

        System.out.println("init mvc sucess ...");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Handler handler = getHandle(req);
        if (handler == null) {
            throw new RuntimeException("请求未找到相应处理器：" + req.getRequestURI());
        }
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();

        Object[] paramValues = new Object[parameterTypes.length];

        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Entry<String, String[]> param : parameterMap.entrySet()) {
            String paramStr = StringUtils.join(param.getValue(), ",");
            if (handler.getParamIndexmapping().containsKey(param.getKey())) {
                Integer index = handler.getParamIndexmapping().get(param.getKey());
                paramValues[index] = paramStr;
            }
        }

        Integer requestIndex = handler.getParamIndexmapping().get(HttpServletRequest.class.getSimpleName());
        if (requestIndex != null) {
            paramValues[requestIndex] = req;
        }

        Integer responseIndex = handler.getParamIndexmapping().get(HttpServletResponse.class.getSimpleName());
        if (responseIndex != null) {
            paramValues[responseIndex] = resp;
        }

        try {
            Object invoke = handler.getMethod().invoke(handler.getController(), paramValues);
            resp.getWriter().write(invoke.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    private Handler getHandle(HttpServletRequest req) {
        for (Pattern pattern : handleMapping.keySet()) {
            if (pattern.matcher(req.getRequestURI()).find()) {
                return handleMapping.get(pattern);
            }
        }
        return null;
    }

    private void doLoadConfig(String configPath) {
        properties = new Properties();
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configPath);
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doScan(String scanPackage) {
        String scanpath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + scanPackage.replaceAll("\\.", "\\/");
        File pack = new File(scanpath);
        File[] files = pack.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")){
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
                if (aClass.isAnnotationPresent(SangedonController.class)) {
                    String simpleName = aClass.getSimpleName();
                    String lowerFirst = lowerFirst(simpleName);
                    Object instance = aClass.newInstance();
                    IocMap.put(lowerFirst, instance);
                } else if (aClass.isAnnotationPresent(SangedonService.class)) {
                    SangedonService sangedonService = aClass.getAnnotation(SangedonService.class);
                    String beanName = sangedonService.value();
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
                    if (!field.isAnnotationPresent(SangedonAutowired.class)) {
                        continue;
                    }
                    String autowiredValue = field.getAnnotation(SangedonAutowired.class).value();
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

    private void initHandleMapping() {
        if (IocMap.isEmpty()) {
            return;
        }

        for (Entry<String, Object> objectEntry : IocMap.entrySet()) {
            Class<?> aClass = objectEntry.getValue().getClass();
            if (!aClass.isAnnotationPresent(SangedonController.class)) {
                continue;
            }

            String baseUrl = "";
            if (aClass.isAnnotationPresent(SangedonRequestMapping.class)) {
                SangedonRequestMapping requestMapping = aClass.getAnnotation(SangedonRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(SangedonRequestMapping.class)) {
                    continue;
                }

                SangedonRequestMapping requestMapping = method.getAnnotation(SangedonRequestMapping.class);
                String value = requestMapping.value();
                String uri = baseUrl + value;
                Handler handler = new Handler(objectEntry.getValue(), method, Pattern.compile(uri));

                Parameter[] parameters = method.getParameters();
                int index = 0;
                for (Parameter parameter : parameters) {
                    if (parameter.getType() == HttpServletRequest.class || parameter.getType() == HttpServletResponse.class) {
                        handler.getParamIndexmapping().put(parameter.getType().getSimpleName(), index++);
                    } else {
                        handler.getParamIndexmapping().put(parameter.getName(), index++);
                    }
                }

                handleMapping.put(Pattern.compile(uri), handler);
            }
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
