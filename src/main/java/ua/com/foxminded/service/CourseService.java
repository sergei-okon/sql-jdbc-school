package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.db.dao.CourseDao;
import ua.com.foxminded.model.Course;

import java.util.List;

@Service
public class CourseService {

    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Course findByName(String name) {
        return courseDao.findByName(name);
    }

    public Course findById(Integer id) {
        return courseDao.findById(id);
    }

    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public void create(Course course) {
        courseDao.create(course);
    }

    public void update(Integer id, Course updateCourse) {
        courseDao.update(id, updateCourse);
    }

    public void delete(Integer id) {
        courseDao.delete(id);
    }
}
