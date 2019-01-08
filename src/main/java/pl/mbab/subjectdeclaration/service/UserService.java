package pl.mbab.subjectdeclaration.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.mbab.subjectdeclaration.model.User;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.web.dto.UserRegistrationDto;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    void addCourse(String login, Long courseId);

    List<Course> showBasket(String login);
}
