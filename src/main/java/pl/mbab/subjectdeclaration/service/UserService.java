package pl.mbab.subjectdeclaration.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.web.dto.UserRegistrationDto;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User findByPesel(String pesel);

    User save(UserRegistrationDto registration);

    void addCourse(String login, Long courseId);

    void deleteCourse(String login, Long courseId);

    List<Course> showBasket(String login);

    void addCompulsoryCourse(User user);

    List<Course> getFieldCourses(String login, boolean group1);

    Course[][] timetable(String login);

    void validate(String login);

}
