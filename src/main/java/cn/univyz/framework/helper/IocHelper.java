package cn.univyz.framework.helper;

import cn.univyz.framework.annotation.Inject;
import cn.univyz.framework.util.ArrayUtil;
import cn.univyz.framework.util.CollectionUtil;
import cn.univyz.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 *
 * @author bfy
 * @since 1.0.0
 */

public class IocHelper {

    static {
        // 获取所有的 Bean 实例之间的映射关系（简称 Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)){
            //遍历 Bean Map
            for (Map.Entry<Class<?>,Object>beanEntry : beanMap.entrySet()){
                //从BeanMap 中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取 BeanMap 中获取 Bean 类与 Bean 实例
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)){
                    //遍历 Bean Field
                    for (Field beanField : beanFields){
                        //判断当前Bean Field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在Bean Map 中获取 Bean Field 对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化 BeanField 的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
