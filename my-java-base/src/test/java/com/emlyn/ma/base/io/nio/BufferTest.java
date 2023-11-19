package com.emlyn.ma.base.io.nio;

import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class BufferTest {

    @Test // 记得打开调试模式，看看每次操作后的buffer的position, limit, capacity的变化
    public void testBufferClear() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        showBuffer(byteBuffer);
        // 写模式
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        showBuffer(byteBuffer);

        // 读模式
        byteBuffer.flip();
        byte a = byteBuffer.get();
        byte b = byteBuffer.get();

        // 写模式
        byteBuffer.clear();
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        showBuffer(byteBuffer);
    }

    @Test
    public void testBufferCompact() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        showBuffer(byteBuffer);
        // 写模式
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        showBuffer(byteBuffer);

        // 读模式
        byteBuffer.flip();
        byte a = byteBuffer.get();
        byte b = byteBuffer.get();
        showBuffer(byteBuffer);

        // 写模式
        byteBuffer.compact();
        byteBuffer.put((byte) 4);
        byteBuffer.put((byte) 5);
        byteBuffer.put((byte) 6);
        byteBuffer.put((byte) 7);
        byteBuffer.put((byte) 8);
        byteBuffer.put((byte) 9);
        byteBuffer.put((byte) 10);
        showBuffer(byteBuffer);
    }

    private void showBuffer(Buffer buffer) {
        System.out.print("position: " + buffer.position() + ", ");
        System.out.print("limit: " + buffer.limit() + ", ");
        System.out.println("capacity: " + buffer.capacity());
    }

}
