package ua.com.foxminded.service;

import org.springframework.stereotype.Service;
import ua.com.foxminded.db.dao.GroupDao;
import ua.com.foxminded.model.Group;

import java.util.List;

@Service
public class GroupService {

    private final GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> findAllGroupsWithLessOrEqualsStudentCount(Integer count) {
        return groupDao.findAllGroupsWithLessOrEqualsStudentCount(count);
    }

    public void create(Group group) {
        groupDao.create(group);
    }

    public void delete(Integer id) {
        groupDao.delete(id);
    }
}
