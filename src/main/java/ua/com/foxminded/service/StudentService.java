package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.db.dao.StudentDao;
import ua.com.foxminded.model.Student;

import java.util.List;

@Service
public class StudentService {

    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student findById(Integer id) {
        return studentDao.findById(id);
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public void create(Student student) {
        studentDao.create(student);
    }

    public void update(Integer id, Student student) {
        studentDao.update(id, student);
    }

    public void delete(Integer id) {
        studentDao.delete(id);
    }

    public List<Student> findAllStudentByCourseName(String nameCourse) {
        return studentDao.findAllStudentByCourseName(nameCourse);
    }

    public void removeStudentFromCourses(Integer studentId, Integer coursesId) {
        studentDao.removeStudentFromCourses(studentId, coursesId);
    }

    public Integer addStudentToCourseById(Integer studentId, Integer courseId) {
        return studentDao.addStudentToCourseById(studentId, courseId);
    }
}