package pl.mbab.subjectdeclaration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.service.CourseService;
import pl.mbab.subjectdeclaration.service.UserService;

@Slf4j
@Controller
public class UserController {

    private UserService userService;
    private CourseService courseService;

    public UserController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping({"/", "/index"})
    public String root(Authentication authentication, Model model) {
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

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/schedule")
    public String showList(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        Course[][] basket = userService.timetable(email);
        model.addAttribute("basket", basket);
        return "schedule";
    }

    @GetMapping("/description")
    public String description() {
        return "description";
    }

    @GetMapping("/rules")
    public String rules() {
        return "rules";
    }

    @PostMapping("/validate")
    public String postValidation(Authentication authentication, RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        courseService.validate(email);
        redirectAttributes.addFlashAttribute("message", "success");
        return "redirect:/index";
    }

    @GetMapping("/validate")
    public String getValidation() {
        return "index";
    }
}