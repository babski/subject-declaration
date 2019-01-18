package pl.mbab.subjectdeclaration.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mbab.subjectdeclaration.model.user.Field;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.service.FieldService;
import pl.mbab.subjectdeclaration.service.UserService;
import pl.mbab.subjectdeclaration.web.dto.UserRegistrationDto;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;


    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        List<Field> fieldsub = fieldService.getAllFields();
        model.addAttribute("fieldsub", fieldsub);
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result, Model model){
        List<Field> fieldsub = fieldService.getAllFields();
        model.addAttribute("fieldsub", fieldsub);
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "Istnieje już konto zarejestrowane na ten adres email");
        }

        if (result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }

}