package ua.com.foxminded.model;

import javax.validation.constraints.*;
import java.util.List;

public class Student {

    private Integer id;

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    @Pattern(regexp = "\\p{L}+")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    @Pattern(regexp = "\\p{L}+")
    private String lastName;

    @NotNull(message = "GroupId should not be empty")
    @Min(value = 1, message = "GroupId should be greater 0")
    private Integer groupId;

    private List<Integer> coursesId;

    public Student(Integer id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(Integer id, String firstName, String lastName, Integer groupId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupId = groupId;
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
                ", coursesId=" + coursesId +
                '}';
    }
}
