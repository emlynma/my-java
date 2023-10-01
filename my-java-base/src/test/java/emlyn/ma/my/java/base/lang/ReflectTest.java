package emlyn.ma.my.java.base.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectTest {

    @Test
    void testClassInfo() throws Exception {
        Class<?> clazz = ArrayList.class;
        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.getCanonicalName());
        System.out.println(clazz.getClassLoader());
        System.out.println(clazz.arrayType());
        System.out.println(clazz.getConstructor());
        System.out.println(Arrays.toString(clazz.getConstructors()));
        System.out.println(Arrays.toString(clazz.getDeclaredConstructors()));
        System.out.println(Arrays.toString(clazz.getMethods()));
        System.out.println(Arrays.toString(clazz.getDeclaredMethods()));
        System.out.println(Arrays.toString(clazz.getFields()));
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));
        System.out.println(Arrays.toString(clazz.getAnnotations()));
        System.out.println(Arrays.toString(clazz.getDeclaredAnnotations()));
        System.out.println(Arrays.toString(clazz.getInterfaces()));
        System.out.println(Arrays.toString(clazz.getGenericInterfaces()));
        System.out.println(Arrays.toString(clazz.getAnnotatedInterfaces()));
        System.out.println(Arrays.toString(clazz.getTypeParameters())); // 范型参数
    }

    @Test
    @SuppressWarnings("unchecked")
    void testInvoke() throws Exception {
        Class<?> clazz = ArrayList.class;
        // construct
        ArrayList<Object> list = (ArrayList<Object>) clazz.getConstructor().newInstance();
        // add
        Method add = clazz.getMethod("add", Object.class);
        add.invoke(list, "hello");
        Assertions.assertEquals(1, list.size());
    }

    @Test
    void testParseAnnotation() {
        Class<?> clazz = ReflectTest.class;
        for (Method method : clazz.getDeclaredMethods()) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getName().equals(Test.class.getName())) {
                    System.out.println("found @Test: " + method.getName());
                }
            }
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void testDynamicProxy() {
        ArrayList<Object> list = new ArrayList<>();
        List<Object> proxyList = (List<Object>) Proxy.newProxyInstance(
                list.getClass().getClassLoader(),
                list.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    System.out.println("before invoke");
                    Object result = method.invoke(list, args);
                    System.out.println("after invoke");
                    return result;
                }
        );
        proxyList.add("hello");
    }

}
