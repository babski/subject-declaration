package pl.mbab.subjectdeclaration.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mbab.subjectdeclaration.converter.UserConverter;
import pl.mbab.subjectdeclaration.dto.UserDto;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.model.user.UserStatus;
import pl.mbab.subjectdeclaration.repository.CourseRepository;
import pl.mbab.subjectdeclaration.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private EmailService emailService;
    private MessageSource messageSource;
    private CourseRepository courseRepository;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           EmailService emailService, MessageSource messageSource,
                           CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.emailService = emailService;
        this.messageSource = messageSource;
        this.courseRepository = courseRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByPesel(String pesel) {
        return userRepository.findByPesel(pesel);
    }

    @Transactional
    public User save(UserDto userDto) {
        User user = userConverter.convertFromDto(userDto);
        userRepository.save(user);
        addCompulsoryCourse(user);
        sendActivationMail(user);
        return user;
    }

    @Override
    public void addCompulsoryCourse(User user) {
        List<Course> userCourses = user.getCourseBasket();
        userCourses.add(courseRepository.findById(Long.valueOf(user.getSemester().ordinal() + 1))
                .orElse(null));
    }

    @Override
    public void sendActivationMail(User user) {
        String activationTitle = messageSource.getMessage("mail.activation.title",
                new Object[]{user.getFirstName()}, Locale.getDefault());
        String mailText = messageSource.getMessage("mail.activation.text",
                new Object[]{user.getPesel(), user.getToken()}, Locale.getDefault());
        emailService.sendConfirmationMessage(user.getEmail(), activationTitle, mailText);
    }

    @Override
    public boolean activateUser(String pesel, String token) {
        User user = userRepository.findOneByPeselAndToken(pesel, token);
        if (user != null && UserStatus.NEW.equals(user.getUserStatus())) {
            user.setUserStatus(UserStatus.ACTIVATED);
            userRepository.save(user);
            return true;
        }
        throw new RuntimeException("Error while trying to activate user");
    }

    @Override
    public boolean authenticate(String email, String password) {
        return userRepository.findUserByEmailAndPasswordAndUserStatus
                (email, password, UserStatus.ACTIVATED) != null ? true : false;
    }

    @Override
    public Course[][] timetable(String email) {
        User user = findByEmail(email);
        List<Course> basket = user.getCourseBasket();
        Course[][] coursesInBasket = new Course[7][5];
        for (Course singleCourse : basket) {
            coursesInBasket[timeParser(singleCourse.getStartTime())]
                    [singleCourse.getDay().ordinal()] = singleCourse;
        }
        return coursesInBasket;
    }

    public static int timeParser(LocalTime localTime) {
        for (int i = 0; i < 7; i++) {
            if (localTime.equals(LocalTime.of(8, 00, 00).plusMinutes(110 * i)))
                return i;
        }
        throw new RuntimeException("Nieprawidłowa godzina rozpoczęcia zajęć.");
    }
}