package pl.mbab.subjectdeclaration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mbab.subjectdeclaration.dto.UserDto;
import pl.mbab.subjectdeclaration.model.user.Field;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.service.FieldService;
import pl.mbab.subjectdeclaration.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        List<Field> fieldsub = fieldService.getAllFields();
        model.addAttribute("fieldsub", fieldsub);
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
                                      BindingResult result, Model model){
        List<Field> fieldsub = fieldService.getAllFields();
        model.addAttribute("fieldsub", fieldsub);
        User existingEmail = userService.findByEmail(userDto.getEmail());
        User existingPesel = userService.findByPesel(userDto.getPesel());
        if (existingEmail != null){
            result.rejectValue("email", null,
                    "Istnieje już konto zarejestrowane na ten adres email");
        }
        if (existingPesel != null){
            result.rejectValue("pesel", null,
                    "Istnieje już konto zarejestrowane na ten nr PESEL");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }

    @GetMapping("/user/activate")
    public String activateUser(@RequestParam("pesel") String pesel,
                               @RequestParam("token") String token, Model model) {
        boolean activated=userService.activateUser(pesel, token);

        String message;
        if (activated) {
            message = "user.activated";
        } else {
            message = "user.activated.fail";
        }
        model.addAttribute("message", messageSource.getMessage(message,new Object[]
                {pesel}, Locale.getDefault() ));
        return "activation-message";
    }
}