package com.example.redisdemo.IO.BIO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServerSocket {
    //先定义一个端口号，这个端口的值是可以自己调整的。
    static final int DEFAULT_PORT=8080;



    public static void main(String[] args) throws IOException, InterruptedException {
        final int DEFAULT_PORT=8080;
        ServerSocket serverSocket= new ServerSocket(DEFAULT_PORT);
        System.out.println("启动服务，监听端口："+DEFAULT_PORT);
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        while(true) {
            Socket socket = serverSocket.accept();
            executorService.submit(new SocketThread(socket));
        }
    }
}
