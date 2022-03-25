package com.example.redisdemo.IO.BIO;

import java.io.*;
import java.net.Socket;

public class SocketThread implements Runnable{
    Socket socket;

    public SocketThread(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        System.out.println("客户端：" + socket.getPort() + "已连接");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientStr = null; //读取一行信息
            clientStr = bufferedReader.readLine();
            System.out.println("客户端发了一段消息：" + clientStr);
            Thread.sleep(20000);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("I have got your message \n");
            bufferedWriter.flush(); //清空缓冲区触发消息发送
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
