package com.nxg.day13;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterTest {
    public static void main(String[] args) {
        //1.    创建FileWriter 类型的对象与d:/a.txt 文件关联
        //如果需要使用\ 则需要转义 即d:\\a.txt
        FileWriter fileWriter = null;
        try {

            fileWriter = new FileWriter("d:/a.txt");
            //使用追加的方式会保留文件中原有的内内容
            //fileWriter = new FileWriter("d:/a.txt",true);
            //2.    向输出流中写入内容
            //fileWriter.write("a");
            fileWriter.write(97); //本质上就是写入 'a'

            //准备一个字符数组,在写入
            char[] chars = new char[]{'f','d','c','k'};
            //将数组chars中下标从1开始的3个字符写到输出流中
            fileWriter.write(chars,1,3);
            //写入整个字符数组
            fileWriter.write(chars);
            System.out.println("写入数据成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //3.关闭流对象并释放有关的资源
            try {
                if (fileWriter != null){
                    fileWriter.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }






    }


}
