package ua.com.foxminded.model;

public class Student {

    private Integer id;
    private String first_name;
    private String last_name;
    private String group;
    private String course1;
    private String course2;
    private String course3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCourse1() {
        return course1;
    }

    public void setCourse1(String course1) {
        this.course1 = course1;
    }

    public String getCourse2() {
        return course2;
    }

    public void setCourse2(String course2) {
        this.course2 = course2;
    }

    public String getCourse3() {
        return course3;
    }

    public void setCourse3(String course3) {
        this.course3 = course3;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + first_name + '\'' +
                ", surname='" + last_name + '\'' +
                ", group='" + group + '\'' +
                ", course1='" + course1 + '\'' +
                ", course2='" + course2 + '\'' +
                ", course3='" + course3 + '\'' +
                '}';
    }
}
