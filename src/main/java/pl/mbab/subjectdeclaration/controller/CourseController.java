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

import java.util.List;

@Slf4j
@Controller
public class CourseController {

    private CourseService courseService;
    private UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/courses")
    public String listCourses(Model model){
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "courses";
    }

    @GetMapping("/showlist")
    public String showList(){
        return "showlist";
    }

    @GetMapping("/basket")
    public String showBasket(Model model, Authentication authentication){
        String login = authentication.getName();
        User user = userService.findByEmail(login);
        model.addAttribute("user", user);
        return "basket";
    }

    @PostMapping("/addcourse")
    public String addCourseById(@RequestParam("courseId") String courseId, Authentication authentication) {
        String login = authentication.getName();
        log.debug(login);
        Long id = Long.parseLong(courseId);
        userService.addCourse(login, id);
        return "index";
    }
}
