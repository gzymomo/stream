package com.example.redisdemo.IO;

import java.io.*;
import java.net.Socket;

public class BIOClientSocket {
    static final int DEFAULT_PORT=8080;
    public static void main(String[] args) throws IOException {

        //在客户端这边，咱们使用socket来连接到指定的ip和端口
        Socket socket=new Socket("localhost",8080);
        //使用BufferedWriter，像服务器端写入一个消息
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("我是客户端Client-01\n");
        bufferedWriter.flush();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String serverStr=bufferedReader.readLine(); //通过bufferedReader读取服务端返回的消息
        System.out.println("服务端返回的消息："+serverStr);
    }
}
