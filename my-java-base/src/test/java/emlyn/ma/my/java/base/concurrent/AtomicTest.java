package emlyn.ma.my.java.base.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.concurrent.atomic.*;

public class AtomicTest {

    private static int i = 0;

    @Test
    void testCAS() throws NoSuchFieldException, IllegalAccessException {
        VarHandle iHandle = MethodHandles.lookup()
                .findStaticVarHandle(AtomicTest.class, "i", int.class);
        iHandle.compareAndSet(0, 10);
        Assertions.assertEquals(10, i);
    }

    // 原子更新基本类型
    @Test
    public void testAtomicBoolean() {
        AtomicBoolean enable = new AtomicBoolean(false);
        if (enable.compareAndSet(false, true)) {
            Assertions.assertTrue(enable.get());
        }
    }

    @Test
    public void testAtomicInteger() {
        AtomicInteger number = new AtomicInteger(0);
        if (number.compareAndSet(0, 1)) {
            Assertions.assertEquals(1, number.get());
        }
    }

    @Test
    public void testAtomicLong() {
        AtomicLong number = new AtomicLong(100);
        if (number.compareAndSet(100, 200)) {
            Assertions.assertEquals(200, number.get());
        }
    }

    // 原子更新数组
    @Test
    public void testAtomicIntegerArray() {
        AtomicIntegerArray atomicNumbers = new AtomicIntegerArray(new int[] {1, 2, 3, 4});
        atomicNumbers.compareAndSet(2, 3, 30);
        Assertions.assertEquals(30, atomicNumbers.get(2));
    }

    @Test
    public void testAtomicLongArray() {
        AtomicLongArray atomicNumbers = new AtomicLongArray(new long[] {1, 2, 3, 4});
        atomicNumbers.compareAndSet(2, 3, 30);
        Assertions.assertEquals(30, atomicNumbers.get(2));
    }

    @Test
    public void testAtomicReferenceArray() {
        AtomicReferenceArray<String> objectArray = new AtomicReferenceArray<>(new String[]{"1", "2", "3", "4"});
        objectArray.compareAndSet(2, "3", "30");
        Assertions.assertEquals("30", objectArray.get(2));
    }

    // 原子更新引用类型
    @Data
    @AllArgsConstructor
    private static class User {
        volatile String name;
        int age;
    }

    @Test
    public void testAtomicReference() {
        User user = new User("mahaoran", 24);
        AtomicReference<User> userReference = new AtomicReference<>(user);
        User newUser = new User("haoranma", 25);
        userReference.compareAndSet(user, newUser);
        Assertions.assertEquals(newUser, userReference.get());
    }

    @Test
    public void testAtomicMarkableReference() {
        User user = new User("mahaoran", 24);
        AtomicMarkableReference<User> markableReference = new AtomicMarkableReference<>(user, false);
        Assertions.assertFalse(markableReference.isMarked());
        User newUser = new User("haoranma", 25);
        markableReference.compareAndSet(user, newUser, false, true);
        Assertions.assertEquals(newUser, markableReference.getReference());
        Assertions.assertTrue(markableReference.isMarked());
    }

    @Test
    public void testAtomicStampedReference() {
        User user = new User("mahaoran", 24);
        AtomicStampedReference<User> stampedReference = new AtomicStampedReference<>(user, 0);
        Assertions.assertEquals(0, stampedReference.getStamp());
        User newUser = new User("haoranma", 25);
        stampedReference.compareAndSet(user, newUser, 0, 1);
        Assertions.assertEquals(newUser, stampedReference.getReference());
        Assertions.assertEquals(1, stampedReference.getStamp());
    }

    // 原子更新字段类
    @Test
    public void testAtomicIntegerFieldUpdater() {}

    @Test
    public void testAtomicLongFieldUpdater() {}

    @Test
    public void testAtomicReferenceFieldUpdater() {
        User user = new User("mahaoran", 24);
        AtomicReferenceFieldUpdater<User, String> fieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(User.class, String.class, "name");
        fieldUpdater.compareAndSet(user, "mahaoran", "haoranma");
        Assertions.assertEquals("haoranma", user.name);
    }

}
