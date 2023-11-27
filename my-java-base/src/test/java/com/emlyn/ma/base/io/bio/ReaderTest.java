package com.emlyn.ma.base.io.bio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * Closeable
 * Readable
 * Reader
 *     FileReader           // 从文件中读取字符流
 *     PipedReader          // 从管道中读取字符流
 *     CharArrayReader      // 从字符数组中读取字符流
 *     StringReader         // 从字符串中读取字符流
 *     InputStreamReader    // 从字节流中读取字符流
 *     FilterReader         // 从其他流中读取字符流
 *     BufferedReader       // 从其他流中读取字符流
 *
 * Writer
 *     FileWriter           // 向文件中写入字符流
 *     PipedWriter          // 向管道中写入字符流
 *     CharArrayWriter      // 向字符数组中写入字符流
 *     StringWriter         // 向字符串中写入字符流
 *     OutputStreamWriter   // 向字节流中写入字符流
 *     FilterWriter         // 向其他流中写入字符流
 *     BufferedWriter       // 向其他流中写入字符流
 */
public class ReaderTest {

    @Test
    public void testStringReader() {
        String hello = "Hello World!";
        try (var stringReader = new StringReader(hello)) {
            int firstChar = stringReader.read();
            Assertions.assertEquals('H', firstChar);
            char[] chars = new char[10];
            int next10Char = stringReader.read(chars);
            Assertions.assertEquals(10, next10Char);
            Assertions.assertEquals("ello World", new String(chars));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCharArrayReader() {
        char[] chars = "Hello World!".toCharArray();
        try (var charArrayReader = new CharArrayReader(chars)) {
            int firstChar = charArrayReader.read();
            Assertions.assertEquals('H', firstChar);
            char[] next10Char = new char[10];
            int next10CharCount = charArrayReader.read(next10Char);
            Assertions.assertEquals(10, next10CharCount);
            Assertions.assertEquals("ello World", new String(next10Char));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInputStreamReader() {
        try (var inputStreamReader = new InputStreamReader(new FileInputStream("src/test/resources/test.txt"))) {
            int firstChar = inputStreamReader.read();
            Assertions.assertEquals('H', firstChar);
            char[] next10Char = new char[10];
            int next10CharCount = inputStreamReader.read(next10Char);
            Assertions.assertEquals(10, next10CharCount);
            Assertions.assertEquals("ello World", new String(next10Char));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileReader() {
        try (var fileReader = new FileReader("src/test/resources/test.txt")) {
            int firstChar = fileReader.read();
            Assertions.assertEquals('H', firstChar);
            char[] next10Char = new char[10];
            int next10CharCount = fileReader.read(next10Char);
            Assertions.assertEquals(10, next10CharCount);
            Assertions.assertEquals("ello World", new String(next10Char));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
