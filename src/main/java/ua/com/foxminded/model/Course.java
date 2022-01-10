package ua.com.foxminded.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Course {

    private Integer id;
    @NotEmpty(message = "Course name should not be empty")
    @Size(min = 2, max = 30, message = "Course name should be between 2 and 30 characters")
    @Pattern(regexp = "\\p{L}+")
    private String name;
    @Size(min = 2, max = 100, message = "Course name should be between 2 and 30 characters")
    private String description;

    public Course(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Course() {

    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
