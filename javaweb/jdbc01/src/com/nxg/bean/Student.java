package com.nxg.bean;

/**
 * @author nxg
 * date 2022/3/7
 * @apiNote
 * 多方
 */
public class Student {
    private int  stuid;
    private String stuname;
    private int teacherId;


    private Teacher taacher;


    public Student(int stuid, String stuname) {
        this.stuid = stuid;
        this.stuname = stuname;
    }

    public Student() {
    }

    public Student(int stuid, String stuname, int teacherId) {
        this.stuid = stuid;
        this.stuname = stuname;
        this.teacherId = teacherId;
    }

    public Teacher getTaacher() {
        return taacher;
    }

    public void setTaacher(Teacher taacher) {
        this.taacher = taacher;
    }

    public int getStuid() {
        return stuid;
    }

    public void setStuid(int stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
