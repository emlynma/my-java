package com.emlynma.java.base.io;

import com.emlynma.java.base.util.ConcurrentUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTests {

    @Test
    public void testBIO() throws InterruptedException {
        Runnable serverTask = () -> {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("server--> server started, listening on port: " + serverSocket.getLocalPort());
                while (!Thread.currentThread().isInterrupted()) {
                    Socket socket = serverSocket.accept(); // 阻塞等待连接
                    System.out.println("server--> accept connection from client: " + socket.getRemoteSocketAddress());
                    // 拿到连接后，交给线程池处理
                    executorService.submit(() -> {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                            String request;
                            char[] buffer = new char[1024];
                            int size;
                            while ((size = reader.read(buffer)) != -1) { // 阻塞等待数据
                                request = new String(buffer, 0, size);
                                if ("Bye".equals(request)) {
                                    writer.write("Bye"); writer.flush();
                                    break;
                                }
                                String clientName = request.split(": ")[1];
                                String response = "hi " + clientName;
                                writer.write(response); writer.flush();
                            }
                            socket.close();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();

        ConcurrentUtils.sleep(1000);

        Thread[] clientThreads = new Thread[10];
        for (int i = 0; i < clientThreads.length; i++) {
            clientThreads[i] = new Thread(new ClientTask());
            clientThreads[i].start();
        }
        for (Thread clientThread : clientThreads) {
            clientThread.join();
        }
        System.out.println("testBIO() finished");
    }

    @Test
    public void testNIO() throws InterruptedException {
        // NIO的关键在于多路复用，单独使用NIO的话，没有太大的意义
    }

    @Test
    public void testMultiplexNIO() throws InterruptedException {
        Runnable serverTask = () -> {
            // 创建Selector 打开ServerSocketChannel
            try (var selector = Selector.open(); var serverSocketChannel = ServerSocketChannel.open()) {
                // 切换非阻塞模式
                serverSocketChannel.configureBlocking(false);
                // 绑定服务器地址和端口号
                serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8080));
                // 注册ServerSocketChannel到Selector并监听ACCEPT事件
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                // 阻塞等待事件发生;
                while (selector.select() > 0) {
                    // 获取事件
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    // 处理事件
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            // 获取SocketChannel
                            SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                            // 切换非阻塞模式
                            socketChannel.configureBlocking(false);
                            // 注册SocketChannel到Selector并监听READ事件
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            // 获取SocketChannel
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            // 读取数据
                            String request;
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            if (socketChannel.read(buffer) == -1) {
                                socketChannel.close();
                            } else {
                                buffer.flip(); // 切换到读模式
                                request = new String(buffer.array(), 0, buffer.remaining());
                                buffer.clear(); // 切换到写模式
                                // 响应数据
                                if ("Bye".equals(request)) {
                                    String response = "Bye";
                                    buffer.put(response.getBytes());
                                    buffer.flip(); // 切换到读模式
                                    socketChannel.write(buffer);
                                    socketChannel.close();
                                } else {
                                    String response = "Hi " + request.split(": ")[1];;
                                    buffer.put(response.getBytes());
                                    buffer.flip(); // 切换到读模式
                                    socketChannel.write(buffer);
                                }
                            }
                        } else if (key.isWritable()) {
                            System.out.println("server--> writable");
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();

        ConcurrentUtils.sleep(1000);

        Thread[] clientThreads = new Thread[10];
        for (int i = 0; i < clientThreads.length; i++) {
            clientThreads[i] = new Thread(new ClientTask());
            clientThreads[i].start();
        }
        for (Thread clientThread : clientThreads) {
            clientThread.join();
        }
        System.out.println("testBIO() finished");
    }

    @Test
    public void testAIO() {

    }

    private static class ClientTask implements Runnable {
        @Override
        public void run() {
            String serverHost = "127.0.0.1";
            int serverPort = 8080;
            try (Socket socket = new Socket(serverHost, serverPort)) {
                String localHost = socket.getLocalAddress().getHostAddress();
                int localPort = socket.getLocalPort();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                // 发送消息给服务器
                String request = "Hello, I'm client: " + localHost + ":" + localPort;
                writer.write(request);
                writer.flush();
                System.out.println("client--> request: " + request);
                // 读取服务器的响应
                String response;
                char[] buffer = new char[1024];
                int size;
                if ((size = reader.read(buffer)) == -1) {
                    socket.close();
                } else {
                    response = new String(buffer, 0, size);
                    System.out.println("client--> response: " + response);
                }

                ConcurrentUtils.sleep(500);

                request = "Bye";
                writer.write(request);
                writer.flush();
                System.out.println("client--> request: " + request);
                buffer = new char[1024];
                if ((size = reader.read(buffer)) == -1) {
                    socket.close();
                } else {
                    response = new String(buffer, 0, size);
                    System.out.println("client--> response: " + response);
                }

                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
