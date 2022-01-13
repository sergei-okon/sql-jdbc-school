package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.db.dao.CourseDao;
import ua.com.foxminded.model.Course;

@Service
public class CourseService {

    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public void create(Course course) {
        courseDao.create(course);
    }

    public void delete(Integer id) {
        courseDao.delete(id);
    }
}
