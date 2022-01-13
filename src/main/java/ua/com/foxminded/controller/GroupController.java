package ua.com.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.foxminded.service.GroupService;

@Controller
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/form")
    public String formGroupsEqualsStudentCount() {
        return "groups/form-find-groups-equals-student-count";
    }

    @GetMapping("/find")
    public String findGroupsEqualsStudentCount(Model model, @RequestParam("count") Integer count) {
        model.addAttribute("groups", groupService.findAllGroupsWithLessOrEqualsStudentCount(count));
        return "groups/find-groups-equals-student-count";
    }
}
