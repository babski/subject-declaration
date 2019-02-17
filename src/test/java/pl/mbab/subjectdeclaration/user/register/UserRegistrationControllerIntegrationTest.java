package pl.mbab.subjectdeclaration.user.register;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.mbab.subjectdeclaration.model.user.Gender;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.security.provider.DatabaseAuthenticationProvider;
import pl.mbab.subjectdeclaration.service.FieldService;
import pl.mbab.subjectdeclaration.service.UserService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.mbab.subjectdeclaration.user.register.UserRegistrationController.REGISTRATION;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserRegistrationController.class)
@ComponentScan(basePackageClasses = UserRegistrationController.class)
public class UserRegistrationControllerIntegrationTest {

    @MockBean
    private UserService userService;

    @MockBean
    private FieldService fieldService;

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private DatabaseAuthenticationProvider authenticationProvider;

    @Autowired
    private MockMvc mockMvc;

    private static Stream<Arguments> invalidUserDto() {
        final UserDto userDto = new UserDto();
        return Stream.of(
                Arguments.arguments(userDto.toBuilder().firstName("").build(), "firstName"),
                Arguments.arguments(userDto.toBuilder().firstName("AA").build(), "firstName"),
                Arguments.of(userDto.toBuilder().lastName("").build(), "lastName"),
                Arguments.of(userDto.toBuilder().pesel("").build(), "pesel"));
    }

    @Test
    public void submitRegistrationSuccess() throws Exception {
        mockMvc.perform(post("/registration")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "janusz@januszewski.pl")
                .param("confirmEmail", "janusz@januszewski.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl(UserRegistrationController.REGISTRATION_SUCCESS_PAGE))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void submitRegistrationPeselExists() throws Exception {
        when(userService.findByPesel(anyString())).thenReturn(new User());

        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "janusz@januszewski.pl")
                .param("confirmEmail", "janusz@januszewski.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","pesel"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationEmailExists() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(new User());

        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "janusz@januszewski.pl")
                .param("confirmEmail", "janusz@januszewski.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","email"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationEmailNotCompliant() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "adam.@wp.pl")
                .param("confirmEmail", "adam.@wp.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","email"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationPeselNotCompliant() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861153")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "adam@wp.pl")
                .param("confirmEmail", "adam@wp.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","pesel"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationPasswordNotCompliant() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861153")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!")
                .param("confirmPassword", "Poiuytrewq!")
                .param("email", "adam@wp.pl")
                .param("confirmEmail", "adam@wp.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","password"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationPasswordNotMatching() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!3")
                .param("email", "adam@wp.pl")
                .param("confirmEmail", "adam@wp.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","password"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationEmailNotMatching() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "adam@wp.pl")
                .param("confirmEmail", "adamek@wp.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","email"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationTermsNotAccepted() throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", "Janusz")
                .param("lastName", "Januszewski")
                .param("pesel", "65110861152")
                .param("gender", Gender.MALE.name())
                .param("semester", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "adam@wp.pl")
                .param("confirmEmail", "adam@wp.pl")
                .param("terms", "off"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user","terms"))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("invalidUserDto")
    public void test(UserDto userDto, String invalidKey) throws Exception {
        mockMvc.perform(post(REGISTRATION)
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", userDto.getFirstName())
                .param("lastName", userDto.getLastName())
                .param("pesel", userDto.getPesel())
                .param("gender", Gender.MALE.name())
                .param("semestr", "I")
                .param("password", "Poiuytrewq!2")
                .param("confirmPassword", "Poiuytrewq!2")
                .param("email", "janusz@januszewski.pl")
                .param("confirmEmail", "janusz@januszeswsk.pl")
                .param("terms", "on"))
                .andDo(print())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", invalidKey));
    }
}
