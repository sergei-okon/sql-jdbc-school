import ua.com.foxminded.initial.InitialData;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Groups  " + InitialData.createGroups(10, 2, 2));
        System.out.println("Courses   " + InitialData.createCourses() + "\n");
        System.out.println("Students\n" + InitialData.createFullName());


    }
}
