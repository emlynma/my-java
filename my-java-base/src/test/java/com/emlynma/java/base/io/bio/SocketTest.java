package com.emlynma.java.base.io.bio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTest {

    @Test
    public void testSimpleSocket() throws InterruptedException {
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
                        String response = "hi " + request;
                        outputStream.write(response.getBytes());
                    }
                    System.out.println("server--> bye");
                    System.out.println();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Runnable client = () -> {
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try (Socket socket = new Socket("127.0.0.1", 8080)) {
                int port = socket.getLocalPort();
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                for (int i = 0; i < 3; i++) {
                    outputStream.write((String.valueOf(port)).getBytes());
                    System.out.format("client(%d)--> request: %d\n", port, port);
                    byte[] buffer = new byte[32];
                    int size = inputStream.read(buffer);
                    String response = new String(buffer, 0, size);
                    System.out.format("client(%d)--> response: %s\n", port, response);
                    try {
                        Thread.sleep(2000); // mock send request
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

    @Test
    public void testThreadSocket() throws InterruptedException {
        // 容量为5的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Runnable server = () -> {
            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("server--> server started, listening on port: " + serverSocket.getLocalPort());
                System.out.println();
                while (!Thread.currentThread().isInterrupted()) {

                    Socket socket = serverSocket.accept(); // 阻塞等待连接
                    System.out.println("server--> accept connection from client: " + socket.getRemoteSocketAddress());

                    executorService.execute(() -> {
                        try {
                            InputStream inputStream = socket.getInputStream();
                            OutputStream outputStream = socket.getOutputStream();
                            byte[] buffer = new byte[32];
                            int size;
                            while ((size = inputStream.read(buffer)) != -1) { // 阻塞等待数据
                                String request = new String(buffer, 0, size);
                                String response = "hi " + request;
                                outputStream.write(response.getBytes());
                            }
                            System.out.println("server--> bye");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Runnable client = () -> {
            try {
                Thread.sleep((int)(Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try (Socket socket = new Socket("127.0.0.1", 8080)) {
                int port = socket.getLocalPort();
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                for (int i = 0; i < 3; i++) {
                    outputStream.write((String.valueOf(port)).getBytes());
                    System.out.format("client(%d)--> request: %d\n", port, port);
                    byte[] buffer = new byte[32];
                    int size = inputStream.read(buffer);
                    String response = new String(buffer, 0, size);
                    System.out.format("client(%d)--> response: %s\n", port, response);
                    try {
                        Thread.sleep(2000); // mock send request
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

}
