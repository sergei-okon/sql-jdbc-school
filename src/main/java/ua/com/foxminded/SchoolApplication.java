package ua.com.foxminded;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import ua.com.foxminded.service.InitialData;

import java.io.IOException;

@SpringBootApplication
public class SchoolApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SchoolApplication.class, args);

//        System.out.println("Data initialization _______________________________________________");
//        InitialData initialData = new InitialData();
//        initialData.addCoursesToDataBase();
//        System.out.println("------------------------------------------------------------------");
//        initialData.addGroupsToDataBase();
//        System.out.println("------------------------------------------------------------------");
//        initialData.addStudentsToDataBase();
//        System.out.println("------------------------------------------------------------------");
    }
}
