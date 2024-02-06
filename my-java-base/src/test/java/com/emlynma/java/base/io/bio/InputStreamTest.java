package com.emlynma.java.base.io.bio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * Closeable
 * InputStream
 *     FileInputStream          // 从文件中读取字节流
 *     PipedInputStream         // 从管道中读取字节流
 *     ByteArrayInputStream     // 从字节数组中读取字节流
 *     ObjectInputStream        // 从对象中读取字节流
 *     FilterInputStream        // 从其他流中读取字节流
 *         BufferedInputStream
 *         DataInputStream
 *
 *  OutputStream
 *      FileOutputStream         // 向文件中写入字节流
 *      PipedOutputStream        // 向管道中写入字节流
 *      ByteArrayOutputStream    // 向字节数组中写入字节流
 *      ObjectOutputStream       // 向对象中写入字节流
 *      FilterOutputStream       // 向其他流中写入字节流
 *          BufferedOutputStream
 *          DataOutputStream
 *          PrintStream
 */
public class InputStreamTest {

    @Test
    public void testFileInputStream() {
        try (FileInputStream fileIS = new FileInputStream("src/test/resources/test.txt")) {
            // 读取一个字节
            int firstByte = fileIS.read();
            Assertions.assertEquals('H', firstByte);
            // 读取10个字节
            byte[] bytes = new byte[10];
            int next10Byte= fileIS.read(bytes);
            Assertions.assertEquals(10, next10Byte);
            Assertions.assertEquals("ello World", new String(bytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPipeInputStream() {
        try (var inputStream = new PipedInputStream(); var outputStream = new PipedOutputStream()) {
            // Connect the input stream to the output stream
            inputStream.connect(outputStream);
            // Create a producer thread that writes data to the output stream
            Thread producerThread = new Thread(() -> {
                try {
                    String message = "Hello, pipe!";
                    outputStream.write(message.getBytes());
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // Create a consumer thread that reads data from the input stream
            Thread consumerThread = new Thread(() -> {
                try {
                    int data;
                    while ((data = inputStream.read()) != -1) {
                        System.out.print((char) data);
                    }
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            // Start the producer and consumer threads
            producerThread.start();
            consumerThread.start();
            producerThread.join();
            consumerThread.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testByteArrayInputStream() {
        byte[] data = { 65, 66, 67, 68, 69 }; // Byte array
        try (var inputStream = new ByteArrayInputStream(data)) {
            int byteData;
            while ((byteData = inputStream.read()) != -1) {
                System.out.print((char) byteData); // Convert byte to char and print
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectInputStream() {
        try (var outputStream = new ByteArrayOutputStream(100); var objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject("Hello, world!");
            objectOutputStream.flush();
            try (var inputStream = new ByteArrayInputStream(outputStream.toByteArray()); var objectInputStream = new ObjectInputStream(inputStream)) {
                String str = (String) objectInputStream.readObject();
                Assertions.assertEquals("Hello, world!", str);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test // 流包装器类
    public void testFilterInputStream() {
        // 缓冲包装流
        try (FileInputStream fileIS = new FileInputStream("src/test/resources/test.txt")) {
            BufferedInputStream bufferedIS = new BufferedInputStream(fileIS);
            int firstByte = bufferedIS.read();
            Assertions.assertEquals('H', firstByte);
            bufferedIS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 数据包装流
        try (FileInputStream fileIS = new FileInputStream("src/test/resources/test.txt")) {
            DataInputStream dataIS = new DataInputStream(fileIS);
            int firstInt = dataIS.readInt();
            Assertions.assertEquals(1214606444, firstInt);
            dataIS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
