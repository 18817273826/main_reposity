package com.proxy.example.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyExample implements InvocationHandler {

    private Object target = null;

    // 1, 建立代理对象和真实对象之间的关系
    public Object bind(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    // 2，实现代理的逻辑方法
    // 重写
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("在调度真实对象之前的服务");
        Object object = method.invoke(target, args);
        System.out.println("在调度真实对象之后的服务");
        return object;
    }
}
