package emlyn.ma.my.java.base.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceTest {

    @Test // GC 绝不回收强引用的对象
    void testStrongReference() {
        Object o = new Object();
        System.gc();
        System.out.println(o);
        Assertions.assertNotNull(o);
    }

    @Test // GC 在内存不足时回收软引用的对象
    void testSoftReference() {
        SoftReference<Object> srf = new SoftReference<>(new Object());
        System.gc();
        System.out.println(srf.get());
        Assertions.assertNotNull(srf.get());
    }

    @Test // GC 在下一次回收时回收弱引用的对象
    void testWeakReference() {
        WeakReference<Object> wrf = new WeakReference<>(new Object());
        System.gc();
        System.out.println(wrf.get());
        Assertions.assertNull(wrf.get());
    }

    @Test // GC 在下一次回收时回收虚引用的对象
    void testPhantomReference() {
        PhantomReference<Object> prf = new PhantomReference<>(new Object(), new ReferenceQueue<>());
        System.gc();
        System.out.println(prf.get());
        Assertions.assertNull(prf.get());
    }

}
