package ua.com.foxminded.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.foxminded.model.Student;
import ua.com.foxminded.service.StudentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "students/index";
    }

    @GetMapping("/{id}")
    public String showStudentById(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "students/info";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "students/new";
        }
        studentService.create(student);
        return "redirect:/students/";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        studentService.delete(id);
        return "redirect:/students/";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("student", studentService.findById(id));
        return "students/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("student") @Valid Student student,
                         BindingResult bindingResult, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "students/edit";
        }
        studentService.update(id, student);
        return "redirect:/students/";
    }

    @GetMapping("/form/find-by-course-name")
    public String formFindAllStudentByCourseName() {
        return "students/form-find-students-by-course-name";
    }

    @GetMapping("/find/by-course-name")
    public String findAllStudentByCourseName(Model model, @RequestParam("name") String name) {
        model.addAttribute("students", studentService.findAllStudentByCourseName(name));
        return "students/find-students-course-by-name";
    }


    @GetMapping("/form/remove-from-course")
    public String formRemoveStudentFromCourses() {
        return "students/form-remove-student-from-courses";
    }

    @PostMapping("/remove-by-courses")
    public String RemoveStudentFromCourses(@RequestParam("studentId") Integer studentId,
                                           @RequestParam("coursesId") Integer coursesId) {
        studentService.removeStudentFromCourses(studentId, coursesId);
        return "redirect:/students/";
    }

    @GetMapping("/form-delete-id")
    public String formDeleteById() {
        return "students/form-delete-by-id";
    }

    @PostMapping("/delete-by-id")
    public String deleteById(@RequestParam("id") Integer id) {
        studentService.delete(id);
        return "redirect:/students/form-delete-id/";
    }

    @GetMapping("/form/add-to-course")
    public String formAddStudentToCourseById() {
        return "students/add-to-course";
    }

    @PostMapping("/add-to-course")
    public String AddStudentToCourseById(@RequestParam("studentId") Integer studentId,
                                         @RequestParam("coursesId") Integer coursesId) {
        studentService.addStudentToCourseById(studentId, coursesId);
        return "redirect:/students/";
    }
}

