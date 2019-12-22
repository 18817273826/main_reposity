package com.proxy.example;

import com.proxy.example.facade.HelloWorld;
import com.proxy.example.jdkproxy.JDKProxyExample;
import com.proxy.example.service.HelloWorldImpl;
import jdk.nashorn.internal.runtime.regexp.JdkRegExp;

public class ProxyMain {

    public static void main(String[] args) {

        JDKProxyExample jdkProxyExample = new JDKProxyExample();
        HelloWorld proxy = (HelloWorld)jdkProxyExample.bind(new HelloWorldImpl());
        proxy.sayHelloWorld();
    }
}
