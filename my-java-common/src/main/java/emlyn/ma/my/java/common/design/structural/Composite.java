package emlyn.ma.my.java.common.design.structural;

import java.util.ArrayList;
import java.util.List;

public class Composite {

    // 抽象组件接口
    interface FileSystemComponent {
        void display();
    }

    // 叶子类
    static class File implements FileSystemComponent {
        private final String name;

        public File(String name) {
            this.name = name;
        }

        @Override
        public void display() {
            System.out.println("File: " + name);
        }
    }

    // 组合类
    static class Directory implements FileSystemComponent {
        private final String name;
        private final List<FileSystemComponent> components;

        public Directory(String name) {
            this.name = name;
            this.components = new ArrayList<>();
        }

        public void addComponent(FileSystemComponent component) {
            components.add(component);
        }

        public void removeComponent(FileSystemComponent component) {
            components.remove(component);
        }

        @Override
        public void display() {
            System.out.println("Directory: " + name);
            for (FileSystemComponent component : components) {
                component.display();
            }
        }
    }

    // 客户端代码
    public static void main(String[] args) {
        FileSystemComponent file1 = new File("file1.txt");
        FileSystemComponent file2 = new File("file2.txt");
        FileSystemComponent file3 = new File("file3.txt");

        Directory directory1 = new Directory("directory1");
        directory1.addComponent(file1);
        directory1.addComponent(file2);

        Directory directory2 = new Directory("directory2");
        directory2.addComponent(file3);
        directory2.addComponent(directory1);

        directory2.display();
    }

}
