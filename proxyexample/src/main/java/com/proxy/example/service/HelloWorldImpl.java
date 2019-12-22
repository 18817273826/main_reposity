package com.proxy.example.service;

import com.proxy.example.facade.HelloWorld;

public class HelloWorldImpl implements HelloWorld {
    public void sayHelloWorld() {
        System.out.println("hello world");
    }
}
