package pl.mbab.subjectdeclaration.user.register;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.mbab.subjectdeclaration.model.user.Field;
import pl.mbab.subjectdeclaration.service.FieldService;
import pl.mbab.subjectdeclaration.service.UserService;

import java.util.List;

import static java.util.Locale.getDefault;

@Controller
@RequestMapping(UserRegistrationController.REGISTRATION)
@RequiredArgsConstructor
class UserRegistrationController {

    static final String REGISTRATION = "/registration";
    static final String REGISTRATION_SUCCESS_PAGE = "/registration?success";
    private static final String REGISTRATION_PAGE = "registration";
    private static final String USER_ACTIVATED = "user.activated";
    private static final String USER_ACTIVATED_FAIL = "user.activated.fail";
    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String ACTIVATION_MESSAGE_PAGE = "activation-message";
    private final UserService userService;

    private final FieldService fieldService;

    private final MessageSource messageSource;

    private final UserRegistrationValidator userRegistrationValidator;

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        prepareFields(model);
        return REGISTRATION_PAGE;
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Validated UserDto userDto,
                                      BindingResult result,
                                      Model model) {
        prepareFields(model);
        if (registerValidationFailed(userDto, result)) {
            return REGISTRATION_PAGE;
        }

        userService.save(userDto);
        return "redirect:" + REGISTRATION_SUCCESS_PAGE;
    }

    private boolean registerValidationFailed(UserDto userDto, BindingResult result) {
        return userSentInvalidData(result) || businessRulesBroken(userDto, result);
    }

    private boolean userSentInvalidData(BindingResult result) {
        return result.hasErrors();
    }

    private boolean businessRulesBroken(UserDto userDto, BindingResult bindingResult) {
        final UserRegistrationValidateContext validateContext = userRegistrationValidator.validateRegistration(userDto);
        if (validateContext.hasErrors()) {
            validateContext.populateErrors(bindingResult);
            return true;
        }
        return false;
    }

    private void prepareFields(Model model) {
        List<Field> fieldsub = fieldService.getAllFields();
        model.addAttribute("fieldsub", fieldsub);
    }

    @GetMapping("/user/activate")
    public String activateUser(@RequestParam("pesel") String pesel,
                               @RequestParam("token") String token, Model model) {
        String message = userService.activateUser(pesel, token) ? USER_ACTIVATED : USER_ACTIVATED_FAIL;
        model.addAttribute(MESSAGE_ATTRIBUTE, messageSource.getMessage(message, new Object[]
                {pesel}, getDefault()));
        return ACTIVATION_MESSAGE_PAGE;
    }
}
