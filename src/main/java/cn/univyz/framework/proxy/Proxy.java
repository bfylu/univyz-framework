package cn.univyz.framework.proxy;

/**
 * 代理接口
 * @author bfy
 * @version 1.0.0
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
