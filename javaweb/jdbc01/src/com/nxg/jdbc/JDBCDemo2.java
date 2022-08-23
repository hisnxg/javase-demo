package com.nxg.jdbc;

import java.sql.*;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote jdbc 增删改
 */
public class JDBCDemo2 {
    public static void main(String[] args){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //1、加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、获得连接
            String url = "jdbc:mysql://localhost:3306/studentsys?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";
            String userName = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, userName, password);
            //3、定义sql语句，创建状态通道（进行sql语句传送），
            statement = connection.createStatement();

            String sql = "select *  from student";
/*            String sql2 = "insert into student(stuname,stuno,phone) values('zhangsan','101010as','16607245876')";
            //增删改 使用executeUpdate。
            int i = statement.executeUpdate(sql2);//返回结果是受影响的行数。
            String sql3 = "update student set stuname='lisi' where stuid=8";
            String sql4 = “delete from student where stuId=7”;
            */

           resultSet = statement.executeQuery(sql);
            
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
                if (statement != null) {
                    statement.close();
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
