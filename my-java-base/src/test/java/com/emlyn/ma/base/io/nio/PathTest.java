package com.emlyn.ma.base.io.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathTest {

    @Test
    public void testFileCRUD() throws IOException {
        String filePath = "src/test/resources/test.txt";
        // Path path1 = FileSystems.getDefault().getPath(filePath);
        Path path = Path.of(filePath);
        // Path path3 = Paths.get(filePath);
        // Path path4 = Paths.get(new URI("file:///src/test/resources/test.txt"));

        if (Files.exists(path)) {
            Files.delete(path);
            System.out.println("Delete file success!");
        }

        Path dir = path.getParent();
        if (Files.exists(dir)) {
            Files.delete(dir);
            System.out.println("Delete dir success!");
        }

        Files.createDirectories(dir);
        System.out.println("Create dir success!");
        Files.createFile(path);
        System.out.println("Create file success!");
        Files.writeString(path, "Hello World!");
    }

}
