package emlyn.ma.my.java.base.io.bio;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileTest {

    @Test
    public void testFileCRUD() throws IOException {
        File file = new File("src/test/resources/test.txt");
        System.out.println(file.getAbsolutePath());
        if (file.exists() && file.delete()) {
            System.out.println("Delete file success!");
        }
        if (file.createNewFile()) {
            try (var fileOS = new FileWriter(file)) {
                fileOS.write("Hello World!");
                System.out.println("Write file success!");
            }
        }
    }

    @Test
    public void testDirectoryCRUD() throws IOException {
        File dir = new File("src/test/resources/test");
        System.out.println(dir.getAbsolutePath());
        if (dir.exists() && dir.delete()) {
            System.out.println("Delete directory success!");
        }
        if (dir.mkdir()) {
            System.out.println("Create directory success!");
        }
    }

    @Test
    public void testTempFile() throws IOException {
        File test = File.createTempFile("test", ".txt");
        if (test.exists() && test.delete()) {
            System.out.println("Delete temp file success!");
        }
        if (test.createNewFile()) {
            System.out.println("Create temp file success!");
        }
    }

}
