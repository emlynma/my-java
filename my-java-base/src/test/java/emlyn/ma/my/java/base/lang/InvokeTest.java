package emlyn.ma.my.java.base.lang;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;

public class InvokeTest {

    @Data
    private static class TestClass {
        private int i = 0;
        public void test() {
            System.out.println("test");
        }
    }

    @Test
    void testMethodHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle testHandle = lookup.findVirtual(TestClass.class, "test", MethodType.methodType(void.class));
        // test.invoke(new TestClass());
        testHandle.bindTo(new TestClass()).invoke();
    }

    @Test
    void testVarHandle() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        VarHandle iHandle = lookup.findVarHandle(TestClass.class, "i", int.class);
        TestClass testClass = new TestClass();
        iHandle.set(testClass, 10);
        System.out.println(iHandle.get(testClass));
    }

    @Test
    void testVarHandleCAS() throws Throwable {
        TestClass testClass = new TestClass();
        VarHandle iHandle = MethodHandles.lookup().findVarHandle(TestClass.class, "i", int.class);
        iHandle.compareAndSet(testClass, 0, 10);
        Assertions.assertEquals(10, testClass.i);
    }

}
