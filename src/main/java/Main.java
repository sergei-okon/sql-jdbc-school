import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.service.InitialData;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        InitialData initialData = new InitialData();
        System.out.println(initialData.createCourses());
        System.out.println(initialData.createGroups(10, 2, 2));
        System.out.println(initialData.createFullName());
//
        GroupDao groupDao = new GroupDao();
//
        System.out.println("\n" + "FIND BY ALL" + groupDao.findAll());

        System.out.println("\n" + "FIND BY ID GROUPS" + groupDao.findById(9));
//
        Group groupWorkout1 = new Group();
        Group groupWorkout2 = new Group();
        Group groupWorkout3 = new Group();
        groupWorkout1.setName("Workout1");
        groupWorkout2.setName("Workout2");
        groupWorkout3.setName("Workout3");
        groupDao.addNew(groupWorkout1);
        groupDao.addNew(groupWorkout2);
        groupDao.addNew(groupWorkout3);
        System.out.println("\n" + "FIND BY ALL" + groupDao.findAll());

        Group groupBio = new Group();
        groupBio.setName("BI-7777777777777777");

        groupDao.update(8, groupBio);
        System.out.println("==================");
        System.out.println("\n" + "FIND BY ALL" + groupDao.findAll());

        groupDao.delete(11);
        System.out.println("\n" + "FIND BY ALL" + groupDao.findAll());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        StudentDao studentDao = new StudentDao();
        System.out.println(studentDao.findAll());

    }
}
