package pl.mbab.subjectdeclaration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class UserRegistrationIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void submitRegistrationSuccess() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92121709205")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field", "3")
                                .param("email", "domino@wp.pl")
                                .param("confirmEmail", "domino@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "on")
                )
                .andExpect(redirectedUrl("/registration?success"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Sql("/user1.sql")
    public void submitRegistrationEmailExists() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "adamek@wp.pl")
                                .param("confirmEmail", "adamek@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationEmailNotCompliant() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "adam.@wp.pl")
                                .param("confirmEmail", "adam.@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "email"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationPeselNotCompliant() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204689")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "nowak@wp.pl")
                                .param("confirmEmail", "nowak@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "pesel"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql("/user2.sql")
    public void submitRegistrationPeselExists() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "nowak@wp.pl")
                                .param("confirmEmail", "nowak@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "pesel"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationPasswordNotMatching() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "nowak@wp.pl")
                                .param("confirmEmail", "nowak@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!3")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationPasswordNotCompliant() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "nowak@wp.pl")
                                .param("confirmEmail", "nowak@wp.pl")
                                .param("password", "Prostehaslo1")
                                .param("confirmPassword", "Prostehaslo1")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "password"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationTermsNotAccepted() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "nowak@wp.pl")
                                .param("confirmEmail", "nowak@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "off")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("user", "terms"))
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationEmailNotMatching() throws Exception {
        this.mockMvc
                .perform(
                        post("/registration")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("firstName", "Adam")
                                .param("lastName", "Nowak")
                                .param("pesel", "92012204682")
                                .param("gender","MALE")
                                .param("semester","II")
                                .param("field","3")
                                .param("email", "nowak@wp.pl")
                                .param("confirmEmail", "kowalski@wp.pl")
                                .param("password", "Poiuytrewq!2")
                                .param("confirmPassword", "Poiuytrewq!2")
                                .param("terms", "on")
                )
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasErrors("user"))
                .andExpect(status().isOk());
    }


}
