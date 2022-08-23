package com.nxg.dao.impl;

import com.nxg.bean.Student;
import com.nxg.bean.Teacher;
import com.nxg.dao.TeacherDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote
 */
public class TeacherDaoImpl implements TeacherDao {
    @Override
    public Teacher queryById(int tid) {
        Connection connection = null;
        PreparedStatement ppst = null;
        ResultSet resultSet = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            //useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false
            String url = "jdbc:mysql://localhost:3306/sys1?serverTimeZone=UTC";
            String username = "root";
            String password = "123456";
             connection = DriverManager.getConnection(url, username, password);
             String sql = "select * from student s,teacher t where s.teacherId = t.tid and t.tid=? ";
            ppst = connection.prepareStatement(sql);
            //绑定老师id
            ppst.setInt(1,tid);

            resultSet = ppst.executeQuery();

            List<Student> list = new ArrayList<Student>();
            Teacher teacher = new Teacher();
            while(resultSet.next()){
                //取出各自的信息
                teacher.setTid(resultSet.getInt("tid"));
                teacher.setTname(resultSet.getString("tname"));

                Student student = new Student();
                student.setStuid(resultSet.getInt("stuid"));
                student.setStuname(resultSet.getString("stuname"));
                //建立学生和老师之间的关系
                list.add(student);
            }
            teacher.setStudentList(list);
            return teacher;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    return null;
    }

    @Override
    public List<Student> getAll() {
        Connection connection = null;
        PreparedStatement ppst = null;
        ResultSet resultSet = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

            //useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false
            String url = "jdbc:mysql://localhost:3306/sys1?serverTimeZone=UTC&userSSL=false";
            String username = "root";
            String password = "123456";
            connection = DriverManager.getConnection(url, username, password);
            String sql = "select * from student s,teacher t where s.teacherId = t.tid and t.tid=? ";
            ppst = connection.prepareStatement(sql);

            resultSet = ppst.executeQuery();

            //创建一个学生集合
            List<Student> list = new ArrayList<Student>();

            while(resultSet.next()) {
                //取出各自的信息
                Student student = new Student();
                student.setStuid(resultSet.getInt("stuid"));
                student.setStuname(resultSet.getString("stuname"));
                Teacher teacher = new Teacher();
                teacher.setTid(resultSet.getInt("tid"));
                teacher.setTname(resultSet.getString("tname"));
                student.setTaacher(teacher);
                //建立学生和老师之间的关系
                list.add(student);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }
}
