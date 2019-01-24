package pl.mbab.subjectdeclaration.service;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mbab.subjectdeclaration.converter.UserConverter;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.Semester;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.model.user.UserStatus;
import pl.mbab.subjectdeclaration.repository.CourseRepository;
import pl.mbab.subjectdeclaration.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConverter userConverter;

    @Autowired
    EmailService emailService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    CourseRepository courseRepository;

    User user;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, userConverter, emailService,
                messageSource, courseRepository);

        user = new User();
    }

    @Test
//    @Sql("/user1.sql")
    void shouldAddCompulsoryCourseSem1() {
        user.setSemester(Semester.I);
        List<Course> basket = user.getCourseBasket();
        userService.addCompulsoryCourse(user);

        assertEquals(basket.size(), 1);
        assertEquals(basket.get(0).getSubject().getSignature(), "200000");
    }

    @Test
    void shouldActivateUser() {
        String pesel = "90062810103";
        String token = "da719dchgchg2dhsakda";
        user.setPesel(pesel);
        user.setToken(token);
        user.setUserStatus(UserStatus.NEW);
        userRepository.save(user);

        boolean activationStatus = userService.activateUser(pesel, token);

        assertTrue(activationStatus);
    }

    @Test
    void shouldAuthenticate() {
        String email = "roman@wp.pl";
        String password = "password";
        UserStatus userStatus = UserStatus.ACTIVATED;

        user.setEmail(email);
        user.setPassword(password);
        user.setUserStatus(userStatus);
        userRepository.save(user);

        boolean activation = userService.authenticate(email, password);

        assertTrue(activation);
    }

    @Test
    void shouldFailToAuthenticate() {
        User user2 = new User();
        String email = "romans@wp.pl";
        String password = "passwordff";
        UserStatus userStatus = UserStatus.NEW;

        user2.setEmail(email);
        user2.setPassword(password);
        user2.setUserStatus(userStatus);
        userRepository.save(user2);

        boolean activation = userService.authenticate(email, password);

        assertFalse(activation);
    }

    @Test
    void shouldFllTimetable() {
        String email = "wojtek@wp.pl";
        user.setSemester(Semester.II);
        user.setEmail(email);
        userRepository.save(user);
        userService.addCompulsoryCourse(user); //j.obcy II: Thursday 17:10
        userRepository.save(user);

        Course[][] schedule = new Course[7][5];
        Hibernate.initialize(schedule);
        schedule = userService.timetable(email);

        assertNull(schedule[0][0]);
        assertNull(schedule[1][1]);
        assertNotNull(schedule[5][3]);
    }

    @Test
    void shouldParseTime() {
        LocalTime localTime = LocalTime.of(15, 20, 00);

        int parsedIndex = UserServiceImpl.timeParser(localTime);

        assertEquals(parsedIndex, 4);
    }


}