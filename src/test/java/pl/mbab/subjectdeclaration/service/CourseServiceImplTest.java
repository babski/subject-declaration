package pl.mbab.subjectdeclaration.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.mbab.subjectdeclaration.exception.*;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.user.Semester;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.repository.CourseRepository;
import pl.mbab.subjectdeclaration.repository.FieldRepository;
import pl.mbab.subjectdeclaration.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CourseServiceImplTest {

    CourseServiceImpl courseService;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FieldRepository fieldRepository;

    User user;

    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl
                (courseRepository, userService, userRepository, fieldRepository);
        user = new User();
    }

    @Test
    void shouldGetAllCourses() {
        List<Course> allCourses = courseService.getAllCourses();

        assertTrue(allCourses.size() > 100);
    }

    @Test
    void shouldFindCourseById() {
        Course sampleCourse = courseService.findCourseById(221L);

        assertNotNull(sampleCourse);
    }

    @Test
    void shouldCountEcts() {
        List<Course> basket = new ArrayList<>();
        basket.add(courseService.findCourseById(6L));
        basket.add(courseService.findCourseById(22L));
        basket.add(courseService.findCourseById(44L));
        basket.add(courseService.findCourseById(99L));
        basket.add(courseService.findCourseById(178L));

        double ectsInBasket = courseService.countEcts(basket);

        assertEquals(16.5, ectsInBasket);
    }

    @Test
    void shouldFindCoursebySubSignature() {
        List<Course> courses = new ArrayList<>();
        courses.add(courseService.findCourseById(6L));
        courses.add(courseService.findCourseById(22L));

        String signature = courses.get(1).getSubject().getSignature();

        String subjectName = courseService.findCoursebySubSignature(signature, courses);

        assertEquals("Rynek kapitałowy", subjectName);
    }

    @Test
    void ShouldFailToAddOfTheSameSignature() {
        String email = "janusz@wp.pl";
        user.setEmail(email);
        userRepository.save(user);
        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(13L));
        userRepository.save(user);

        Throwable exception = assertThrows(CourseOverrideException.class,
                () -> courseService.addCourse(email, 14L));

        assertEquals(exception.getMessage(), "Przedmiot " + basket.get(0).getSubject().getName() +
                " o sygnaturze " + basket.get(0).getSubject().getSignature() +
                " został już dodany do koszyka.");
    }

    @Test
    void shouldFailToAddOfTimeCollision() {
        String email = "kamil@wp.pl";
        user.setEmail(email);
        userRepository.save(user);
        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(15L));
        userRepository.save(user);

        Throwable exception = assertThrows(CourseCollisionException.class,
                () -> courseService.addCourse(email, 18L));

        assertEquals(1, basket.size());

        assertEquals(exception.getMessage(), "Przedmiot " + courseService.findCourseById(18L).getSubject().getName() +
                " o sygnaturze " + courseService.findCourseById(18L).getSubject().getSignature() + " koliduje terminem " +
                "zajęć z przedmiotem " + basket.get(0).getSubject().getName() + " o sygnaturze "
                + basket.get(0).getSubject().getSignature() + ".");
    }

//    @Test
//    void shouldAddCourse() {
//        String email = "rafalek@wp.pl";
//        user.setEmail(email);
//        userRepository.save(user);
//        List<Course> basket = user.getCourseBasket();
//        basket.add(courseService.findCourseById(5L));
//        userRepository.save(user);
//        courseService.addCourse(email, 6L);
//        userRepository.save(user);
//
//        assertEquals(2, basket.size());
////        assertEquals(6L, basket.get(1).getId());
//    }

//    @Test
//    void shouldDeleteCourse() {
//        String email = "kamilek@wp.pl";
//        user.setEmail(email);
//        userRepository.save(user);
//        List<Course> basket = user.getCourseBasket();
//        basket.add(courseService.findCourseById(15L));
//        basket.add(courseService.findCourseById(185L));
//        userRepository.save(user);
//        courseService.deleteCourse(email, 185L);
//        userRepository.save(user);
//
//        assertEquals(1, basket.size());
//    }

    @Test
    void shouldReturnFieldCourses() {
        String email = "kamilek@wp.pl";
        user.setEmail(email);
        user.setField(fieldRepository.findFirstById(2L));
        userRepository.save(user);
        List<Course> fieldCourses = courseService.getFieldCourses(email, true);

        assertEquals(courseService.findCoursebySubSignature("222260", fieldCourses),
                "Inżynieria oprogramowania");
    }

    @Test
    void shouldAddMoreEcts() {
        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(15L));
        basket.add(courseService.findCourseById(58L));
        basket.add(courseService.findCourseById(29L));
        basket.add(courseService.findCourseById(11L));
        basket.add(courseService.findCourseById(151L));
        basket.add(courseService.findCourseById(199L));
        double ectsInBasket = courseService.countEcts(basket);

        Throwable exception = assertThrows(BasketPointsException.class,
                () -> courseService.checkEcts(user));

        assertTrue(ectsInBasket < Semester.ectsMin);

        assertEquals(exception.getMessage(), "Za mało przedmiotów dodanych do koszyka. Dodaj jeszcze " +
                "przedmioty o wartości co najmniej " + (Semester.ectsMin - ectsInBasket) +
                " pkt ECTS");
    }

    @Test
    void shouldRemoveSomeEcts() {
        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(213L));
        basket.add(courseService.findCourseById(513L));
        basket.add(courseService.findCourseById(253L));
        basket.add(courseService.findCourseById(383L));
        basket.add(courseService.findCourseById(4L));
        basket.add(courseService.findCourseById(161L));
        basket.add(courseService.findCourseById(179L));
        basket.add(courseService.findCourseById(379L));
        basket.add(courseService.findCourseById(181L));
        basket.add(courseService.findCourseById(232L));
        basket.add(courseService.findCourseById(232L));
        basket.add(courseService.findCourseById(232L));
        basket.add(courseService.findCourseById(232L));
        basket.add(courseService.findCourseById(567L));
        double ectsInBasket = courseService.countEcts(basket);

        Throwable exception = assertThrows(BasketPointsException.class,
                () -> courseService.checkEcts(user));

        assertTrue(ectsInBasket > Semester.ectsMax);

        assertEquals(exception.getMessage(), "Za dużo przedmiotów dodanych do koszyka. Usuń z koszyka " +
                "przedmioty o wartości co najmniej " + (ectsInBasket - Semester.ectsMax) +
                " pkt ECTS");
    }

    @Test
    void shouldAcceptEcts() {
        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(213L));
        basket.add(courseService.findCourseById(513L));
        basket.add(courseService.findCourseById(253L));
        basket.add(courseService.findCourseById(383L));
        basket.add(courseService.findCourseById(4L));
        basket.add(courseService.findCourseById(161L));
        basket.add(courseService.findCourseById(179L));
        basket.add(courseService.findCourseById(379L));
        double ectsInBasket = courseService.countEcts(basket);

        assertTrue(courseService.checkEcts(user));
    }

    @Test
    void shouldFailComplexCoursesCheck() {
        List<Course> basket = user.getCourseBasket();
        Long id = 102L;
        String subSignature = courseService.findCourseById(id).getSubject().getSignature();
        String subName = courseService.findCourseById(id).getSubject().getName();
        basket.add(courseService.findCourseById(id));

        Throwable exception = assertThrows(ComplexSubjectException.class,
                () -> courseService.checkComplexCourses(user));

        assertEquals(exception.getMessage(), "Dla przedmiotu " +
                subName + " o sygnaturze " + subSignature + " nie dodano wykładu/ćwiczeń");
    }

    @Test
    void shouldPassComplexCoursesCheck() {
        Long id1 = 102L;
        Long id2 = 103L;
        String subSignature1 = courseService.findCourseById(id1).getSubject().getSignature();
        String subSignature2 = courseService.findCourseById(id2).getSubject().getSignature();

        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(id1));
        basket.add(courseService.findCourseById(id2));

        assertTrue(courseService.checkComplexCourses(user));
        assertTrue(subSignature1.equals(subSignature2));
    }

//    @Test
//    void shouldPassCheckFieldCoursesA() {
//        user.setSemester(Semester.III);
//        user.setEmail("robert@wp.pl");
//        user.setField(fieldRepository.findFirstById(4L));
//        userRepository.save(user);
//        List<Course> basket = user.getCourseBasket();
//        basket.add(courseService.findCourseById(176L));
//        basket.add(courseService.findCourseById(152L));
//        basket.add(courseService.findCourseById(173L));
//        basket.add(courseService.findCourseById(458L));
//        basket.add(courseService.findCourseById(426L));
//        basket.add(courseService.findCourseById(395L));
////        userRepository.save(user);
//
//        assertTrue(courseService.checkFieldCourses(user, true));
//    }

    //
//    @Test
//    void shouldPassCheckFieldCoursesB() {
//        user.setSemester(Semester.III);
//        user.setEmail("robet@wp.pl");
//        user.setField(fieldRepository.findFirstById(4L));
//        userRepository.save(user);
//        List<Course> basket = user.getCourseBasket();
//        basket.add(courseService.findCourseById(458L));
//        basket.add(courseService.findCourseById(426L));
//        basket.add(courseService.findCourseById(395L));
//
//        assertTrue(courseService.checkFieldCourses(user,false));
//    }

    @Test
    void shouldFailCheckFieldCoursesA() {
        user.setSemester(Semester.III);
        user.setField(fieldRepository.findFirstById(4L));
        userRepository.save(user);
        List<Course> basket = user.getCourseBasket();
        basket.add(courseService.findCourseById(176L));
        basket.add(courseService.findCourseById(152L));

        assertThrows(FieldCoursesException.class,
                () -> courseService.checkFieldCourses(user, true));
    }
//
//    @Test // if run separately as single - passes
//    void shouldFailCheckFieldCoursesB() {
//        User user5 = new User();
//        user5.setSemester(Semester.III);
//        user5.setField(fieldRepository.findFirstById(4L));
//        userRepository.save(user5);
//        List<Course> basket = user5.getCourseBasket();
//        basket.add(courseService.findCourseById(426L));
//        basket.add(courseService.findCourseById(395L));
//
//        assertThrows(FieldCoursesException.class,
//                () -> courseService.checkFieldCourses(user5, false));
//    }
//
    @Test
    void shouldPassCheckCompulsoryCourses() {
        User user6 = new User();
        user6.setSemester(Semester.I);
        user6.setEmail("robertos@wp.pl");
        userRepository.save(user6);
        List<Course> basket = user6.getCourseBasket();
        basket.add(courseService.findCourseById(36L));
        basket.add(courseService.findCourseById(44L));
        userRepository.save(user6);

        assertTrue(courseService.checkComplexCourses(user6));
    }

    @Test
    void shouldFailCheckCompulsoryCourses() {
        User user4 = new User();
        user4.setSemester(Semester.I);
        user4.setEmail("robertodo@wp.pl");
        userRepository.save(user4);
        List<Course> basket = user4.getCourseBasket();
        basket.add(courseService.findCourseById(36L));
        userRepository.save(user4);

        assertThrows(CompulsoryCourseException.class,
                () -> courseService.checkCompulsoryCourses(user4));
    }
//
//    @Test
//    void validate() {
//        String email = "roberto@wp.pl";
//        user.setSemester(Semester.II);
//        user.setEmail(email);
//        user.setField(fieldRepository.findFirstById(4L));
//        userRepository.save(user);
//        List<Course> basket = user.getCourseBasket();
//        basket.add(courseService.findCourseById(2L));
//        basket.add(courseService.findCourseById(119L));
//        basket.add(courseService.findCourseById(85L));
//        basket.add(courseService.findCourseById(155L));
//        basket.add(courseService.findCourseById(174L));
//        basket.add(courseService.findCourseById(116L));
//        basket.add(courseService.findCourseById(395L));
//        basket.add(courseService.findCourseById(415L));
//        userRepository.save(user);
//
//        assertTrue(courseService.validate(email));
//    }
}