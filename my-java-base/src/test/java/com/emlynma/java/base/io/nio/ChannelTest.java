package com.emlynma.java.base.io.nio;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ChannelTest {

    @Test // 阻塞读取文件
    public void testBlockReadFile() {
        Path path = Path.of("src/test/resources/test.txt");
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
