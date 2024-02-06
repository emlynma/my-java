package com.emlynma.java.base.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.Field;

public class AnnotationTest {

    @Documented
    @Retention(RetentionPolicy.RUNTIME) // SOURCE, CLASS, RUNTIME
    @Target({ElementType.TYPE}) // TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE
    private @interface MyClass {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    private @interface MyMethod {
        String value() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    private @interface MyField {
        String value() default "";
    }

    @MyClass("test")
    private static class MyAnnotationTest {
        @MyField("test")
        private String test;
    }

    @Test
    void test() throws Throwable {
        MyClass myClass = MyAnnotationTest.class.getAnnotation(MyClass.class);
        System.out.println(myClass.value());

        MyAnnotationTest myAnnotationTest = new MyAnnotationTest();
        Field test = MyAnnotationTest.class.getDeclaredField("test");
        test.setAccessible(true);
        MyField myField = test.getAnnotation(MyField.class);
        String value = myField.value();
        test.set(myAnnotationTest, value);
        Assertions.assertEquals(value, myAnnotationTest.test);
    }

}
