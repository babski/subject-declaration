package pl.mbab.subjectdeclaration.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.mbab.subjectdeclaration.model.User;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.web.dto.UserRegistrationDto;

import java.util.Set;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);

    void addCourse(String login, Long courseId);

    void deleteCourse(String login, Long courseId);

    Set<Course> showBasket(String login);

    void addCompulsoryCourse(User user);

    Set<Course> getFieldCourses(String login, boolean group1);

    Course[][] timetable(String login);

    void validate(String login);

}
