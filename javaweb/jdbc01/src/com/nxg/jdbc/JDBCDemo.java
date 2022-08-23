package com.nxg.jdbc;

import java.sql.*;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote
 */
public class JDBCDemo {
    public static void main(String[] args){
        Connection connection = null;
        PreparedStatement pps = null;
        ResultSet resultSet = null;

        try {
            //1、加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、获得连接
            String url = "jdbc:mysql://localhost:3306/studentsys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
            String userName = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, userName, password);
/*            //3、定义sql语句，创建状态通道（进行sql语句传送），
            statement = connection.createStatement();*/

            String sql = "select *  from student";
/*            String sql2 = "insert into student(stuname,stuno,phone) values('zhangsan','101010as','16607245876')";
            //增删改 使用executeUpdate。
            int i = statement.executeUpdate(sql2);//返回结果是受影响的行数。
            String sql3 = "update student set stuname='lisi' where stuid=8";
            String sql4 = “delete from student where stuId=7”;
            */

          /*  //sql注入
            String uname="aa";
            String pass = " '' or 1=1";//1=1表示恒成立,不管是否成立都执行成功
            String sql5 = "select * from student where username="+uname+" and password="+pass;
           resultSet = statement.executeQuery(sql5);
            */
            //防止sql注入，使用     目前编写的sql语句在数据库中并没有。
            //3、定义sql语句，创建 预状态通道（进行sql语句传送）， ?表示占位，从1开始，
            String sql6 = "select * from student where username = ? and password = ?";
            pps = connection.prepareStatement(sql6);
            //给占位符赋值 (下标,参数) 下标从1开始
            pps.setString(1,"zhangsan");
            pps.setString(2,"123456");

            ResultSet resultSet1 = pps.executeQuery();
            
            //4、取出结果集信息
            while(resultSet.next()){//判断是否有下一个
                //取出数据 ： resultSet.getXXX("列名"); XXX表示数据类型
                System.out.println("结果1："+resultSet.getString("stuname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if (resultSet != null) {
                    resultSet.close();
                }
                if (pps != null) {
                    pps.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (Exception e){
                String message = e.getMessage();
                System.out.println(message);
            }
        }




    }
}
