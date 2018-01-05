package cn.univyz.framework;

import cn.univyz.framework.helper.*;
import cn.univyz.framework.util.ClassUtil;
import cn.univyz.framework.util.CollectionUtil;

/**
 * 加载相应的 Helper 类
 *
 * @author bfy
 * @version 1.0.0
 */
public final class HelperLoader  {

    public static void init() {

        Class<?>[]  classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                CollectionUtil.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList){
            System.out.println("类的路径:"+ cls.getName());
            ClassUtil.loadClass(cls.getName());
        }
    }
}
