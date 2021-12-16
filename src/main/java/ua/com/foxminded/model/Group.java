package ua.com.foxminded.model;

public class Group {

    private Integer id;
    private String name;
    private Integer amountStudents;

    public Group() {
    }

    public Group(String name, Integer amountStudents) {
        this.name = name;
        this.amountStudents = amountStudents;
    }

    public Group(Integer id, String name, Integer amountStudents) {
        this.id = id;
        this.name = name;
        this.amountStudents = amountStudents;
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

    public Integer getAmountStudents() {
        return amountStudents;
    }

    public void setAmountStudents(Integer amountStudents) {
        this.amountStudents = amountStudents;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountStudents=" + amountStudents +
                '}';
    }
}

