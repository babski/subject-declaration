package pl.mbab.subjectdeclaration.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mbab.subjectdeclaration.exception.*;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.subject.CourseType;
import pl.mbab.subjectdeclaration.model.subject.FieldSubject;
import pl.mbab.subjectdeclaration.model.user.Semester;
import pl.mbab.subjectdeclaration.model.user.User;
import pl.mbab.subjectdeclaration.repository.CourseRepository;
import pl.mbab.subjectdeclaration.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private UserService userService;
    private UserRepository userRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserService userService,
                             UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> result = new ArrayList<>();
        courseRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public double countEcts(List<Course> courseList) {
        return courseList.stream()
                .filter(x -> !(x.getSubject().isComplex() == true && x.getType() == CourseType.CLASSES))
                .mapToDouble(x -> x.getSubject().getEcts()).sum();
    }

    @Override
    public String findCoursebySubSignature(String signature, List<Course> courses) {
        for (Course course : courses) {
            if (course.getSubject().getSignature().equals(signature)) {
                return course.getSubject().getName();
            }
        }
        throw new RuntimeException("Nie istnieje przedmiot o wskazanej sygnataurze.");
    }
    @Override
    @Transactional
    public void addCourse(String login, Long courseId) {
        User user = userService.findByEmail(login);
        Course course = findCourseById(courseId);
        List<Course> addedCourses = user.getCourseBasket();
        for (Course addedCourse : addedCourses) {
            if (addedCourse.getSubject().getSignature().equals(course.getSubject().getSignature()) &&
                    addedCourse.getType().equals(course.getType())) {
                throw new CourseOverrideException("Przedmiot " + course.getSubject().getName() +
                        " o sygnaturze " + course.getSubject().getSignature() +
                        " został już dodany do koszyka.");
            }
            if (addedCourse.getDay().equals(course.getDay()) &&
                    addedCourse.getStartTime().equals(course.getStartTime())) {
                throw new CourseCollisionException("Przedmiot " + course.getSubject().getName() +
                        " o sygnaturze " + course.getSubject().getSignature() + " koliduje terminem " +
                        "zajęć z przedmiotem " + addedCourse.getSubject().getName() + " o sygnaturze "
                        + addedCourse.getSubject().getSignature() + ".");
            }
        }
        addedCourses.add(course);
    }

    @Override
    @Transactional
    public void deleteCourse(String login, Long courseId) {
        User user =userService.findByEmail(login);
        Course course = findCourseById(courseId);
        List<Course> userCourses = user.getCourseBasket();
        userCourses.remove(course);
    }

    @Override
    @Transactional
    public void addCompulsoryCourse(User user) {
        List<Course> userCourses = user.getCourseBasket();
        userCourses.add(courseRepository.findById(Long.valueOf(user.getSemester().ordinal() + 1))
                .orElse(null));
    }

    @Override
    public List<Course> getFieldCourses(String login, boolean group1) {
        User user = userService.findByEmail(login);
        List<FieldSubject> allSubjects = user.getField().getFieldSubjects();
        List<String> fieldSignatures = allSubjects.stream()
                .filter(s -> s.isRequired() == group1)
                .map(s -> s.getSubject().getSignature())
                .collect(Collectors.toList());
        List<Course> courses = getAllCourses();
        List<Course> fieldCourses = courses.stream()
                .filter(s -> fieldSignatures.contains(s.getSubject().getSignature()))
//                .sorted(Comparator.comparing(s -> s.getSubject().getSignature()))
                .collect(Collectors.toList());
        return fieldCourses;
    }

    public void checkEcts(User user) {
        List<Course> basket = user.getCourseBasket();
        double ectsInBasket = countEcts(basket);
        if (ectsInBasket < Semester.ectsMin) {
            throw new BasketPointsException("Za mało przedmiotów dodanych do koszyka. Dodaj jeszcze " +
                    "przedmioty o wartości co najmniej " + (Semester.ectsMin - ectsInBasket) +
                    " pkt ECTS");
        }
        if (ectsInBasket > Semester.ectsMax) {
            throw new BasketPointsException("Za dużo przedmiotów dodanych do koszyka. Usuń z koszyka " +
                    "przedmioty o wartości co najmniej " + (ectsInBasket - Semester.ectsMax) +
                    " pkt ECTS");
        }
    }

    public void checkComplexCourses(User user) {
        List<Course> basket = user.getCourseBasket();
        Set<Course> newBasket = new HashSet<>(basket);

        newBasket = newBasket.stream().filter(course -> course.getSubject().isComplex() == true)
                .collect(Collectors.toSet());
        Map<String, List<Course>> courses = newBasket.stream()
                .collect(Collectors.groupingBy(course -> course.getSubject().getSignature()));

        for (String signature : courses.keySet()) {
            List<Course> coursesPerSignature = courses.get(signature);
            long number = coursesPerSignature.stream().count();
            if (number != 2L) {
                throw new ComplexSubjectException("Dla przedmiotu " +
                        findCoursebySubSignature(signature, coursesPerSignature) +
                        " o sygnaturze " + signature + " nie dodano wykładu/ćwiczeń");
            }
        }
    }

    public void checkFieldCourses(User user, boolean group1) {
        String login = user.getEmail();
        List<Course> basket = user.getCourseBasket();
        List<Course> newBasket = new ArrayList<>(basket);
        Map<String, Course> tempMap = new HashMap<>();
        for (Course course : newBasket) {
            tempMap.put(course.getSubject().getSignature(), course);
        }
        List<Course> uniqueList = tempMap.values().stream().collect(Collectors.toList());
        List<Course> field = getFieldCourses(login, group1);

        List<Course> common = uniqueList.stream().filter(course -> field.contains(course))
                .collect(Collectors.toList());
        double fieldEcts = common.stream().mapToDouble(x -> x.getSubject().getEcts()).sum();
        if (group1) {
            if (fieldEcts < user.getSemester().getField1Ects()) {
                throw new FieldCoursesException("Za mało przedmiotów kierunkowych w koszyku. Dodaj " +
                        "jescze przedmioty kierunkowe o wartości przynajmniej " +
                        (user.getSemester().getField1Ects() - fieldEcts) + " ECTS.");
            }
        } else {
            if (countEcts(common) < user.getSemester().getField2Ects()) {
                throw new FieldCoursesException("Za mało przedmiotów związanych z kierunkiem w " +
                        "koszyku. Dodaj jescze przedmioty związane z kierunkiem o wartości " +
                        "przynajmniej " + (user.getSemester().getField2Ects() - fieldEcts) + " ECTS.");
            }
        }
    }

    public void checkCompulsoryCourses(User user) {
        List<Course> basket = user.getCourseBasket();
        List<Course> newBasket = new ArrayList<>(basket);
        if (user.getSemester().getCompulsorySubjects() != null) {
            String[] compulsory = user.getSemester().getCompulsorySubjects();
            for (String subject : compulsory) {
                if (!newBasket.stream()
                        .filter(x -> x.getSubject().getSignature().equals(subject)).findFirst()
                        .isPresent()) {
                    List<Course> allCourses = getAllCourses();
                    throw new CompulsoryCourseException("W koszyku brakuje obowiązkowego przedmiotu " +
                            findCoursebySubSignature(subject, allCourses) +
                            " o sygnaturze " + subject + ".");
                }
            }
        }
    }

    @Override
    @Transactional
    public void validate(String login) {
        User user = userService.findByEmail(login);
        checkEcts(user);
        checkFieldCourses(user, true);
        checkFieldCourses(user, false);
        checkCompulsoryCourses(user);
        checkComplexCourses(user);
        user.setBasketAccepted(true);
        userRepository.save(user);
    }
}