import ua.com.foxminded.db.dao.GroupDao;
import ua.com.foxminded.db.dao.StudentDao;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.InitialData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Data initialization ________________________________________________");
        InitialData initialData = new InitialData();

        initialData.addCoursesToDataBase();
        System.out.println("------------------------------------------------------------------");
        initialData.addGroupsToDataBase();
        System.out.println("------------------------------------------------------------------");
        initialData.addStudentsToDataBase();
        System.out.println("------------------------------------------------------------------");

        Integer coutStudentsInGroup = 20;
        System.out.println("Find all groups with less or equals student count " + coutStudentsInGroup);
        GroupDao groupDao1 = new GroupDao();
        System.out.println(groupDao1.findAllGroupsWithLessOrEqualsStudentCount(coutStudentsInGroup));
        System.out.println("------------------------------------------------------------------");

        System.out.println("------------------------------------------------------------------");
        StudentDao studentDao = new StudentDao();
        String course = "Mathematics";
        System.out.println("Find all students related to course with given name " + course);
        List<Student> st = studentDao.findAllStudentByCourseName(course);
        System.out.println(st);

        System.out.println("------------------------------------------------------------------");
        List<Integer> coursesId = new ArrayList<>();
        coursesId.add(1);
        coursesId.add(7);
        Student newStudent = new Student("Peter", "Volkov", 2, coursesId);
        studentDao.create(newStudent);

        System.out.println("------------------------------------------------------------------");
        studentDao.delete(1);

        System.out.println("------------------------------------------------------------------");
        studentDao.addStudentToCourseById(201, 8);
        System.out.println("Added student to course");

        System.out.println("------------------------------------------------------------------");
        studentDao.removeStudentFromCourses(200, 9);

    }
}
