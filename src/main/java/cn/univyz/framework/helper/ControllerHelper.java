package cn.univyz.framework.helper;

import cn.univyz.framework.annotation.Action;
import cn.univyz.framework.bean.Handler;
import cn.univyz.framework.bean.Request;
import cn.univyz.framework.util.ArrayUtil;
import cn.univyz.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 *
 * @author bfy
 * @version 1.0.0
 */
public final class ControllerHelper  {
    /**
     * 用于存放请求与处理器的映射关系（简称Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有的Controller 类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        //System.out.println("ControllerHelper---->"+CollectionUtil.isNotEmpty(controllerClassSet));
        CollectionUtil collectionUtil;

        if (CollectionUtil.isNotEmpty(controllerClassSet)) {
            //遍历这些 Controller 类
            for (Class<?> controllerClass : controllerClassSet) {

                //获取Controller类中定义的方法
                Method[] methosds = controllerClass.getDeclaredMethods();
                if (ArrayUtil.isNotEmpty(methosds)) {
                    //遍历这些 Controller类中的方法
                    for (Method method : methosds){
                        //判断当前方法是否带有 Action 注解
                        if (method.isAnnotationPresent(Action.class)) {
                            //从 Action 注解中获取 URL 映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证 URL 映射规则

                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length == 2 ) {
                                    //获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);

                                    Handler handler = new Handler(controllerClass, method);
                                    //初始化 Action Map
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("ControllerHelper---------->isEist");

        }
    }


    /**
     * 获取 Handler
     */
    public static Handler getHandler(String requestMethod,String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }

}
