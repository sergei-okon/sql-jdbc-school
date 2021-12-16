package ua.com.foxminded.service;

import ua.com.foxminded.dao.CourseDao;
import ua.com.foxminded.dao.GroupDao;
import ua.com.foxminded.dao.StudentDao;
import ua.com.foxminded.model.Course;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Student;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class InitialData {

    private static final int STUDENTS_TOTAL_AMOUNT = 200;
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC = "0123456789";

    public void addGroupsToDB() {
        List<Group> groups = creatGroupsRandomAmountStudents
                (200, 10, 30);
        GroupDao groupDao = new GroupDao();

        for (Group group : groups) {
            groupDao.addNew(group);
        }
    }

    public void addStudentsToDB() throws IOException {
        List<Student> students = assignCoursesStudents();
        StudentDao studentDao = new StudentDao();
        List<Integer> studentCourse;

        for (int i = 0; i < 200; i++) {
            studentDao.addNew(students.get(i));

            studentCourse = new ArrayList<>(students.get(i).getCoursesId());

            for (int i1 = 0; i1 < studentCourse.size(); i1++) {

                studentDao.addStudentToCourseById(students.get(i).getId(), studentCourse.get(i1));
            }
        }
    }

    public void addCoursesToDb() throws IOException {
        Set<Course> courseList = createCourses();
        CourseDao courseDao = new CourseDao();

        for (Course course : courseList) {
            courseDao.addNew(course);
        }
    }

    public List<Student> assignCoursesStudents() throws IOException {
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

    public List<Student> assignFulNameToGroups() throws IOException {
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
        GroupDao groupDao = new GroupDao();
        groupDao.addNew(groupWithoutStudents);

        for (Student student : studentsWithoutGroup) {
            student.setGroupId(11);
        }
        students.addAll(studentsWithoutGroup);

        int countId = 1;
        for (Student student : students) {
            student.setId(countId);
            countId++;
        }
        return students;
    }

    public List<Student> createStudentsFullName(Integer amountStudents) throws IOException {
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

    public Set<Course> createCourses() throws IOException {
        Set<Course> courses = new HashSet<>();
        List<String> coursesTemp;
        try {
            coursesTemp = Arrays.stream(new String(Objects.requireNonNull(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("courses.txt")).readAllBytes(),
                    StandardCharsets.UTF_8).split("\r\n")).toList();

            int countId = 1;
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

    public List<Group> creatGroupsRandomAmountStudents
            (Integer amountStudents, Integer minAmountStudentsGroup, Integer maxAmountStudentsGroup) {

        List<Group> groups = createGroups(10, 2, 2);

        int amountStudentsInGroups = 0;
        for (Group value : groups) {

            int randomAmountStudents = ThreadLocalRandom.current().nextInt(minAmountStudentsGroup, maxAmountStudentsGroup + 1);
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

    public List<Group> createGroups(int numberGroups, int lengthWords, int lengthNumeric) {
        List<Group> groups = new ArrayList<>();

        int countId = 1;
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

    private String generateOneGroupsName(int lengthWords, int lengthNumeric) {

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
