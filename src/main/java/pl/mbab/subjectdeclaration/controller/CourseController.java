package pl.mbab.subjectdeclaration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.service.CourseService;
import pl.mbab.subjectdeclaration.service.UserService;

import java.util.List;
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
    public String listCourses(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Set<Course> courses = courseService.getAllCourses()
                .stream().filter(x -> !x.getLecturer().getSignature().equals("0000"))
                .collect(Collectors.toSet());
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "courses";
    }

    @GetMapping("/courses/fielda")
    public String fieldCoursesA(Authentication authentication, Model model) {
        String email = authentication.getName();
        boolean group1 = true;
        User user = userService.findByEmail(email);
        List<Course> courses = courseService.getFieldCourses(email, group1);
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "fielda";
    }

    @GetMapping("/courses/fieldb")
    public String fieldCoursesB(Authentication authentication, Model model) {
        String email = authentication.getName();
        boolean group1 = false;
        User user = userService.findByEmail(email);
        List<Course> courses = courseService.getFieldCourses(email, group1);
        model.addAttribute("courses", courses);
        model.addAttribute("user", user);
        return "fieldb";
    }

    @PostMapping("/addcourse")
    public String addCourseById(@RequestParam("courseId") String courseId, Authentication authentication) {
        String email = authentication.getName();
        log.debug(email);
        Long id = Long.parseLong(courseId);
        courseService.addCourse(email, id);
        return "redirect:/";
    }

    @PostMapping("/deletecourse")
    public String deleteCourseById(@RequestParam("courseId") String courseId, Authentication authentication) {
        String email = authentication.getName();
        log.debug(email);
        Long id = Long.parseLong(courseId);
        courseService.deleteCourse(email, id);
        return "redirect:/";
    }
}