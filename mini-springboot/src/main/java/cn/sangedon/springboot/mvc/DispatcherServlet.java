package cn.sangedon.springboot.mvc;

import cn.sangedon.springboot.boot.ApplicationContext;
import cn.sangedon.springboot.mvc.annotation.Controller;
import cn.sangedon.springboot.mvc.annotation.RequestMapping;
import cn.sangedon.springboot.mvc.handle.Handler;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
public class DispatcherServlet extends HttpServlet {

    private List<String> classNameList = new ArrayList<>();

    private ApplicationContext applicationContext;

    private Map<Pattern, Handler> handleMapping = new HashMap<>();

    public DispatcherServlet(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
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

    private void initHandleMapping() {
        if (applicationContext.getIocMap().isEmpty()) {
            return;
        }

        for (Entry<String, Object> objectEntry : applicationContext.getIocMap().entrySet()) {
            Class<?> aClass = objectEntry.getValue().getClass();
            if (!aClass.isAnnotationPresent(Controller.class)) {
                continue;
            }

            String baseUrl = "";
            if (aClass.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = aClass.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }

            Method[] methods = aClass.getMethods();
            for (Method method : methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }

                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
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

    private Handler getHandle(HttpServletRequest req) {
        for (Pattern pattern : handleMapping.keySet()) {
            if (pattern.matcher(req.getRequestURI()).find()) {
                return handleMapping.get(pattern);
            }
        }
        return null;
    }
}
