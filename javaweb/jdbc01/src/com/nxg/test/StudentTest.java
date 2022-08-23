package com.nxg.test;

import com.nxg.bean.Student;
import com.nxg.bean.Teacher;
import com.nxg.dao.TeacherDao;
import com.nxg.dao.impl.TeacherDaoImpl;

import java.util.List;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote
 */
public class StudentTest {

    public static void main(String[] args) {

         TeacherDao teacherDao = new TeacherDaoImpl();
        Teacher teacher = teacherDao.queryById(1);
        System.out.println("teacher="+teacher);
        List<Student> studentList = teacher.getStudentList();
        for (Student student : studentList) {
            System.out.println("学生="+student.getStuname()+",老师="+teacher.getTname());

        }

        List<Student> all = teacherDao.getAll();
        for (Student student : all) {
            System.out.println("studnet="+student.getStuname()+"老师"+student.getTaacher().getTname());
        }
    }
}
