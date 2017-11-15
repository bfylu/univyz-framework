package cn.univyz.framework;

import cn.univyz.framework.helper.BeanHelper;
import cn.univyz.framework.helper.ClassHelper;
import cn.univyz.framework.helper.ControllerHelper;
import cn.univyz.framework.helper.IocHelper;
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
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
