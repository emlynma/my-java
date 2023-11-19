package com.emlyn.ma.base.io.bio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

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
