package com.emlyn.ma.base.io.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;

public class SocketTest {

    @Test
    public void testNonBlockIO() throws InterruptedException {
        Runnable server = () -> {
            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("server--> server started, listening on port: " + serverSocket.getLocalPort());
                System.out.println();
                while (!Thread.currentThread().isInterrupted()) {

                    Socket socket = serverSocket.accept(); // 阻塞等待连接
                    System.out.println("server--> accept connection from client: " + socket.getRemoteSocketAddress());

                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();
                    byte[] buffer = new byte[32];
                    int size;
                    while ((size = inputStream.read(buffer)) != -1) { // 阻塞等待数据
                        String request = new String(buffer, 0, size);
                        Thread.sleep(1);
                        String response = "hi " + request;
                        outputStream.write(response.getBytes());
                    }
                    System.out.println("server--> bye");
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable client = () -> {
            try (var clientChannel = SocketChannel.open()) {
                // 连接服务器
                clientChannel.connect(new InetSocketAddress("localhost", 8080));
                clientChannel.configureBlocking(false);
                int port = getPort(clientChannel.getLocalAddress());
                // 发送消息给服务器
                for (int i = 0; i < 2; i++) {
                    // 发送消息
                    String request = String.valueOf(port);
                    ByteBuffer buffer = ByteBuffer.allocate(32);
                    buffer.put(request.getBytes());
                    buffer.flip();
                    clientChannel.write(buffer);
                    buffer.clear();
                    System.out.format("client(%d)--> request: %d\n", port, port);
                    // 读取响应
                    int size = 0;
                    while ((size = clientChannel.read(buffer)) != -1) {
                        // 非阻塞IO的精髓，没有数据时，直接返回0，而不是阻塞等待
                        if (size == 0) {
                            System.out.println("client--> waiting response...");
                        }
                        if (size > 0) {
                            buffer.flip();
                            byte[] responseBytes = new byte[size];
                            buffer.get(responseBytes);
                            String response = new String(responseBytes);
                            System.out.format("client(%d)--> response: %s\n", port, response);
                            break;
                        }
                    }
                }
                System.out.format("client(%d)--> bye\n", port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread serverThread = new Thread(server);
        serverThread.start();
        Thread clientThread = new Thread(client);
        clientThread.start();

        clientThread.join();
    }

    @Test
    public void testMultiplexIOSocket() throws InterruptedException {
        Runnable server = () -> {
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
                            ByteBuffer buffer = ByteBuffer.allocate(32);
                            int size;
                            while ((size = socketChannel.read(buffer)) > 0) {
                                assert true;
                            }
                            if (size == -1) {
                                socketChannel.close();
                                System.out.println("server--> bye");
                                continue;
                            } else {
                                buffer.flip();
                                request = new String(buffer.array(), 0, buffer.remaining());
                                buffer.clear();
                                // 响应数据
                                String response = "hi " + request;
                                buffer.put(response.getBytes());
                                buffer.flip();
                                socketChannel.write(buffer);
                                buffer.clear();
                            }
                        }
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable client = () -> {
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try (var clientChannel = SocketChannel.open()) {
                // 连接服务器
                clientChannel.connect(new InetSocketAddress("localhost", 8080));
                int port = getPort(clientChannel.getLocalAddress());
                // 发送消息给服务器
                for (int i = 0; i < 2; i++) {
                    String request = String.valueOf(port);
                    ByteBuffer buffer = ByteBuffer.allocate(32);
                    buffer.put(request.getBytes());
                    buffer.flip();
                    clientChannel.write(buffer);
                    buffer.clear();
                    System.out.format("client(%d)--> request: %d\n", port, port);
                    // 读取服务器响应
                    int bytesRead = clientChannel.read(buffer);
                    buffer.flip();
                    byte[] responseBytes = new byte[bytesRead];
                    buffer.get(responseBytes);
                    String response = new String(responseBytes);
                    System.out.format("client(%d)--> response: %s\n", port, response);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.format("client(%d)--> bye\n", port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        LocalDateTime start = LocalDateTime.now();

        Thread serverThread = new Thread(server);
        serverThread.start();
        Thread.sleep(100);

        Thread clientThread1 = new Thread(client);
        clientThread1.start();

        Thread clientThread2 = new Thread(client);
        clientThread2.start();

        Thread clientThread3 = new Thread(client);
        clientThread3.start();

        clientThread1.join();
        clientThread2.join();
        clientThread3.join();

        LocalDateTime end = LocalDateTime.now();
        System.out.println("time cost: " + Duration.between(start, end).toMillis() + "ms");

    }

    private int getPort(SocketAddress socketAddress) {
        return ((InetSocketAddress) socketAddress).getPort();
    }
}
