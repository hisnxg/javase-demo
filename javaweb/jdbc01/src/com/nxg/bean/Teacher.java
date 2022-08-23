package com.nxg.bean;

import java.util.List;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote
 * 一方
 */
public class Teacher {
    private int tid;
    private String tname;
    //在一方存储多方数据的集合
     private List<Student> studentList;

    public Teacher() {
    }

    public Teacher(int tid, String tname, List<Student> studentList) {
        this.tid = tid;
        this.tname = tname;
        this.studentList = studentList;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
