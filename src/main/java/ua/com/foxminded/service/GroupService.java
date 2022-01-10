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

    public Group findById(Integer id) {
        return groupDao.findById(id);
    }

    public List<Group> findAll() {
        return groupDao.findAll();
    }

    public void create(Group group) {
        groupDao.create(group);
    }

    public void update(Integer id, Group updateGroup) {
        groupDao.update(id, updateGroup);
    }

    public void delete(Integer id) {
        groupDao.delete(id);
    }
}
