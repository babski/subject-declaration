package pl.mbab.subjectdeclaration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbab.subjectdeclaration.model.User;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.service.CourseService;
import pl.mbab.subjectdeclaration.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class CourseController {

    private CourseService courseService;
    private UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses/all")
    public String listCourses(Model model) {
        Set<Course> courses = courseService.getAllCourses()
                .stream().filter(x -> !x.getLecturer().getSignature().equals("0000"))
                .collect(Collectors.toSet());
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/courses/fielda")
    public String fieldCoursesA(Authentication authentication, Model model) {
        String login = authentication.getName();
        boolean group1 = true;
        User user = userService.findByEmail(login);
        Set<Course> courses = userService.getFieldCourses(login, group1);
        model.addAttribute("courses", courses);
        return "fielda";
    }

    @GetMapping("/courses/fieldb")
    public String fieldCoursesB(Authentication authentication, Model model) {
        String login = authentication.getName();
        boolean group1 = false;
        User user = userService.findByEmail(login);
        Set<Course> courses = userService.getFieldCourses(login, group1);
        model.addAttribute("courses", courses);
        return "fieldb";
    }

    @GetMapping("/showlist")
    public String showList(Authentication authentication, Model model) {
        String login = authentication.getName();
        User user = userService.findByEmail(login);
        Course [][] basket = userService.timetable(login);
        model.addAttribute("basket", basket);
        return "showlist";
    }

    @PostMapping("/addcourse")
    public String addCourseById(@RequestParam("courseId") String courseId, Authentication authentication) {
        String login = authentication.getName();
        log.debug(login);
        Long id = Long.parseLong(courseId);
        userService.addCourse(login, id);
        return "redirect:/";
    }

    @PostMapping("/validate")
    public String validate(Authentication authentication) {
        String login = authentication.getName();
        log.debug(login);
        userService.validate(login);
        return "index";
    }

    @PostMapping("/deletecourse")
    public String deleteCourseById(@RequestParam("courseId") String courseId, Authentication authentication) {
        String login = authentication.getName();
        log.debug(login);
        Long id = Long.parseLong(courseId);
        userService.deleteCourse(login, id);
        return "redirect:/";
    }
}
