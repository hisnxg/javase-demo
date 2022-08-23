package com.nxg.day15;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTest {
    public static void main(String[] args) {
        Socket socket = null;
        PrintStream printStream = null;
        Scanner scanner = null;
        try{
            socket = new Socket("100.64.133.240",8888);
            System.out.println("连接服务器成功");
            // 客户端向服务器发送消息
             scanner = new Scanner(System.in);
            System.out.println("向服务器发信息:");
            String msg = scanner.nextLine();
            //2.使用输入输出流通信
             printStream = new PrintStream(socket.getOutputStream());
            //printStream.println("在吗?");
            printStream.print(msg);
            System.out.println("客户端发送数据成功!");

        }catch (Exception e){
            e.getMessage();
        }finally {
            //3.关闭Socket和流对象并释放有关的资源
            if (null !=printStream){
                printStream.close();
            }
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
