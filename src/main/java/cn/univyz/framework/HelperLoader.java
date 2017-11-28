package cn.univyz.framework;

import cn.univyz.framework.helper.*;
import cn.univyz.framework.util.ClassUtil;

/**
 * 加载相应的 Helper 类
 *
 * @author bfy
 * @version 1.0.0
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[]  classList = new Class[]{
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}
