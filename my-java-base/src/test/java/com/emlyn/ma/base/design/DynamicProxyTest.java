package com.emlyn.ma.base.design;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

    private interface Hello {
        void sayHello(String name);
    }

    private interface Bye {
        void sayBye(String name);
    }

    private static class HelloByeImpl implements Hello, Bye {
        @Override
        public void sayHello(String name) {
            System.out.println("Hello " + name);
        }
        @Override
        public void sayBye(String name) {
            System.out.println("Bye " + name);
        }
    }

    @Test
    public void testProxy() {

        // 被代理对象
        HelloByeImpl helloBye = new HelloByeImpl();

        InvocationHandler handler = (proxy, method, args) -> {
            System.out.println("Before invoke " + method.getName());
            Object result = method.invoke(helloBye, args);
            System.out.println("After invoke " + method.getName());
            return result;
        };

        // 代理对象
        Object helloByeProxy = Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class, Bye.class},
                handler);

        ((Hello) helloByeProxy).sayHello("World");
        ((Bye) helloByeProxy).sayBye("World");
    }

}
