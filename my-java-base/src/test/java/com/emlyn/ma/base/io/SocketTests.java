package com.emlyn.ma.base.io;

import com.emlyn.ma.base.util.ConcurrentUtils;
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
            ExecutorService executorService = Executors.newFixedThreadPool(5);
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
                            while ((request = reader.readLine()) != null) { // 阻塞等待数据
                                if ("Bye".equals(request)) {
                                    writer.write("Bye"); writer.newLine(); writer.flush();
                                    break;
                                }
                                String clientName = request.split(": ")[1];
                                String response = "hi " + clientName;
                                writer.write(response); writer.newLine(); writer.flush();
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
                            int size;
                            while ((size = socketChannel.read(buffer)) > 0) {
                                assert true;
                            }
                            if (size == -1) {
                                socketChannel.close();
                            } else {
                                buffer.flip(); // 切换到读模式
                                request = new String(buffer.array(), 0, buffer.remaining());
                                // 响应数据
                                String response = "hi " + request;
                                buffer.clear(); // 切换到写模式
                                buffer.put(response.getBytes());
                                socketChannel.write(buffer);
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
                writer.newLine();
                writer.flush();
                System.out.println("client--> request: " + request);
                // 读取服务器的响应
                String response = reader.readLine();
                System.out.println("client--> response: " + response);

                ConcurrentUtils.sleep(500);

                request = "Bye";
                writer.write(request);
                writer.newLine();
                writer.flush();
                System.out.println("client--> request: " + request);
                response = reader.readLine();
                System.out.println("client--> response: " + response);

                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
