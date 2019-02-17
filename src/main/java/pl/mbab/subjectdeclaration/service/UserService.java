package pl.mbab.subjectdeclaration.service;

import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.user.register.UserDto;

public interface UserService {

    User findByEmail(String email);

    User findByPesel(String pesel);

    User save(UserDto userDto);

    Course[][] timetable(String email);

    void sendActivationMail(User user);

    boolean activateUser(String email, String token);

    boolean authenticate(String email, String password);

    void addCompulsoryCourse(User user);
}
