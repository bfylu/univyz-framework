package cn.univyz.framework.proxy;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器
 *
 * @author byf
 * @version
 */
public class ProxyManager {
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final  Class<?> targetClass, final List<Proxy> proxyList) {
        //使用CGLib提供的Enhancer#create 方法来创建代理对象，
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod,methodProxy, methodParams, proxyList).doProxyChain();
            }
        });

    }
}
