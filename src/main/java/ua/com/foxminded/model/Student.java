package ua.com.foxminded.model;

import java.util.List;

public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer groupId;
    private List<Integer> coursesId;

    public Student(Integer id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(String firstName, String lastName, Integer groupId, List<Integer> coursesId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
        this.coursesId = coursesId;
    }

    public Student(String firstName, String lastName, Integer groupId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getCoursesId() {
        return coursesId;
    }

    public void setCoursesId(List<Integer> coursesId) {
        this.coursesId = coursesId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", groupId=" + groupId +
//                ", coursesId=" + coursesId +
                '}';
    }
}
