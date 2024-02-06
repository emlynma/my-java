package com.emlynma.java.base.io.aio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class AsynchronousTest {

    @Test
    public void testFileRead() throws IOException {
        Path file = Path.of("src/test/resources/test.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(file, StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        fileChannel.read(buffer, 0, null, new CompletionHandler<Integer, Void>() {
            @Override
            public void completed(Integer bytesRead, Void attachment) {
                System.out.println("result = " + bytesRead);
                System.out.println("attachment = " + attachment);
            }
            @Override
            public void failed(Throwable exc, Void attachment) {
                System.out.println("exc = " + exc);
                System.out.println("attachment = " + attachment);
            }
        });
    }

}
