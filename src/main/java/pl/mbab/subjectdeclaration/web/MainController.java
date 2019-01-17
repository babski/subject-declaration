package pl.mbab.subjectdeclaration.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mbab.subjectdeclaration.model.User;
import pl.mbab.subjectdeclaration.service.CourseService;
import pl.mbab.subjectdeclaration.service.UserService;

@Slf4j
@Controller
public class MainController {

    private UserService userService;
    private CourseService courseService;

    public MainController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String root(Authentication authentication, Model model) {
//        String login = authentication.getName();
//        User user = userService.findByEmail(login);
//        if(user.getCourseBasket()== null) {
//            userService.addCompulsoryCourse(login);
//        }
        String login = authentication.getName();
        User user = userService.findByEmail(login);
        double ects = courseService.countEcts(user.getCourseBasket());
        model.addAttribute("user", user);
        model.addAttribute("ects", ects);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

}