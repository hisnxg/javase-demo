package com.nxg.day15;

import sun.awt.windows.WBufferStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {

    public static void main(String[] args) {

        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader bufferedReader = null;
        try{
            //1.创建ServerSocket类型的对象并提供端口号
            serverSocket = new ServerSocket(8888);
            //2.    等待客户端的连接请求,调用accept方法
            System.out.println("等待客户端的连接请求...");
            //若没有客户端连接服务器,则进入阻塞状态
            //若有客户端连接服务器,则解除阻塞状态
             socket = serverSocket.accept();
            System.out.println("客户端连接服务器成功!");
            //2.使用输入输出流通信
             bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String s = bufferedReader.readLine();
            System.out.println("服务器接收到消息是:"+s);
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }finally {
            if (null !=bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != serverSocket){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
