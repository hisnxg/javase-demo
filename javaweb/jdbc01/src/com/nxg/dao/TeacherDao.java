package com.nxg.dao;

import com.nxg.bean.Student;
import com.nxg.bean.Teacher;

import java.util.List;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote
 */
public interface TeacherDao {
    //操作方法

    public Teacher queryById(int tid);

    public List<Student> getAll();
}
