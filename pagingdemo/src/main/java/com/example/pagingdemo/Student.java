package com.example.pagingdemo;

import java.util.Objects;

/**
 * @author JiangHao
 * @date 2020/4/7
 * @describe
 */
public class Student {

    private String id;
    private String name;
    private String gander;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGander() {
        return gander;
    }

    public void setGander(String gander) {
        this.gander = gander;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) &&
                name.equals(student.name) &&
                gander.equals(student.gander);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, gander);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gander='" + gander + '\'' +
                '}';
    }
}
