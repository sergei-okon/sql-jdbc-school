package ua.com.foxminded.service;

import org.springframework.stereotype.Component;
import ua.com.foxminded.db.dao.CourseDao;
import ua.com.foxminded.db.dao.GroupDao;
import ua.com.foxminded.db.dao.StudentDao;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InitialData {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final GroupDao groupDao;

    private static final Integer STUDENTS_TOTAL_AMOUNT = 200;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC = "0123456789";

    public InitialData(CourseDao courseDao, StudentDao studentDao, GroupDao groupDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    public void addGroupsToDataBase() {
        List<Group> groups = creatGroupsRandomAmountStudents
                (200, 10, 30);

        for (Group group : groups) {
            groupDao.create(group);
        }
    }

    public void addStudentsToDataBase() throws IOException {
        List<Student> students = assignCoursesStudents();
        List<Integer> studentCourse;

        for (int i = 0; i < 200; i++) {
            studentDao.create(students.get(i));

            studentCourse = new ArrayList<>(students.get(i).getCoursesId());

            for (Integer integer : studentCourse) {
                studentDao.addStudentToCourseById(students.get(i).getId(), integer);
            }
        }
    }

    public void addCoursesToDataBase() throws IOException {
        Set<Course> courseList = createCourses();

        for (Course course : courseList) {
            courseDao.create(course);
        }
    }

    private List<Student> assignCoursesStudents() throws IOException {
        List<Course> courseList = new ArrayList<>(createCourses());

        List<Student> students = assignFulNameToGroups();

        for (Student student : students) {
            int randomAmountCourses = ThreadLocalRandom.current().nextInt(1, 4);

            Set<Integer> coursesTemp = new HashSet<>();

            for (int i = 0; i < randomAmountCourses; i++) {
                int randomCurrentCourse = ThreadLocalRandom.current().nextInt(1, 10);
                coursesTemp.add(courseList.get(randomCurrentCourse).getId());

            }
            List<Integer> coursesId = new ArrayList<>(coursesTemp);

            student.setCoursesId(coursesId);
        }

        return students;
    }

    private List<Student> assignFulNameToGroups() throws IOException {
        List<Group> groups =
                creatGroupsRandomAmountStudents(STUDENTS_TOTAL_AMOUNT, 10, 30);

        List<Student> students = new ArrayList<>();

        for (Group group : groups) {
            Integer studentsAmount = group.getAmountStudents();
            List<Student> groupStudents = createStudentsFullName(studentsAmount);
            groupStudents.forEach(student -> student.setGroupId(group.getId()));
            students.addAll(groupStudents);
        }

        List<Student> studentsWithoutGroup = createStudentsFullName(STUDENTS_TOTAL_AMOUNT - students.size());
        Group groupWithoutStudents = new Group(11, "StudentWithoutGroup", studentsWithoutGroup.size());

        groupDao.create(groupWithoutStudents);

        for (Student student : studentsWithoutGroup) {
            student.setGroupId(11);
        }
        students.addAll(studentsWithoutGroup);

        Integer countId = 1;
        for (Student student : students) {
            student.setId(countId);
            countId++;
        }
        return students;
    }

    private List<Student> createStudentsFullName(Integer amountStudents) throws IOException {
        List<Student> students = new ArrayList<>();

        List<String> names;
        try {
            names = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader().getResourceAsStream("names.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

        } catch (IOException e) {
            throw new IOException("Error: names file not found");
        }

        List<String> surnames;
        try {
            surnames = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("surnames.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

        } catch (IOException e) {
            throw new IOException("Error: surnames file not found");
        }

        int count = 0;
        for (int i = 0; count < amountStudents; i++) {
            Random random = new Random();
            String surname = surnames.get(i);
            String name = names.get(random.nextInt(20));
            Student student = new Student();

            student.setFirstName(name);
            student.setLastName(surname);
            students.add(student);
            count++;

            if (i == 19) {
                i = 0;
            }
        }

        return students;
    }

    private Set<Course> createCourses() throws IOException {
        Set<Course> courses = new HashSet<>();
        List<String> coursesTemp;
        try {
            coursesTemp = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("courses.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

            Integer countId = 1;
            for (String s : coursesTemp) {
                String[] a = s.split(" ");

                courses.add(new Course(countId, a[0], a[1]));
                countId++;
            }

        } catch (IOException e) {
            throw new IOException("Error: course file not found");
        }

        return courses;
    }

    private List<Group> creatGroupsRandomAmountStudents
            (Integer amountStudents, Integer minAmountStudentsGroup, Integer maxAmountStudentsGroup) {

        List<Group> groups = createGroups(10, 2, 2);

        Integer amountStudentsInGroups = 0;
        for (Group value : groups) {

            Integer randomAmountStudents = ThreadLocalRandom.current().nextInt(minAmountStudentsGroup, maxAmountStudentsGroup + 1);
            amountStudentsInGroups = amountStudentsInGroups + randomAmountStudents;

            if (amountStudentsInGroups + maxAmountStudentsGroup >= amountStudents) {
                value.setAmountStudents(randomAmountStudents);
                break;
            }
            value.setAmountStudents(randomAmountStudents);
        }

        int sumStudentsInGroup = 0;
        for (Group group : groups) {
            sumStudentsInGroup = sumStudentsInGroup + group.getAmountStudents();
        }

        return groups;
    }

    private List<Group> createGroups(Integer numberGroups, Integer lengthWords, Integer lengthNumeric) {
        List<Group> groups = new ArrayList<>();

        Integer countId = 1;
        while (numberGroups > groups.size()) {
            Group group = new Group();

            String nameGroup = this.generateOneGroupsName(lengthWords, lengthNumeric);

            group.setName(nameGroup);
            group.setAmountStudents(0);
            group.setId(countId);
            groups.add(group);

            countId++;
        }

        return groups;
    }

    private String generateOneGroupsName(Integer lengthWords, Integer lengthNumeric) {

        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < lengthWords; i++) {
            result.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        result.append("-");

        for (int i = 0; i < lengthNumeric; i++) {
            result.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
        }
        return result.toString();
    }
}
