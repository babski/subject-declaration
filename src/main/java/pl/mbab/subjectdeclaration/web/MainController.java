package pl.mbab.subjectdeclaration.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mbab.subjectdeclaration.service.UserService;

@Controller
public class MainController {

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String root(Authentication authentication) {
//        String login = authentication.getName();
//        User user = userService.findByEmail(login);
//        if(user.getCourseBasket()== null) {
//            userService.addCompulsoryCourse(login);
//        }
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