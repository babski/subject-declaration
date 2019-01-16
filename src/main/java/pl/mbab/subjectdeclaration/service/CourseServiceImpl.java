package pl.mbab.subjectdeclaration.service;

import org.springframework.stereotype.Service;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.model.subject.CourseType;
import pl.mbab.subjectdeclaration.repository.CourseRepository;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Set<Course> getAllCourses() {
        Set<Course> result = new LinkedHashSet<>();
        courseRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }

    @Override
    public Set<Course> findCourseBySignature() {
        return null;
    }

    @Override
    public Set<Course> findCourseByName() {
        return null;
    }

    @Override
    public Set<Course> findCourseByLecturerId() {
        return null;
    }

    @Override
    public Set<Course> findCourseByLecturerName() {
        return null;
    }

    @Override
    public Set<Course> findFieldCourses() {
        return null;
    }

    @Override
    public Set<Course> addCourse(Long id) {
        return null;
    }

    @Override
    public Set<Course> deleteCourse(Long id) {
        return null;
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public double countEcts(Set<Course> courseSet) {
        return courseSet.stream().filter(x -> !(x.getSubject().isComplex() == true && x.getType() == CourseType.CLASSES))
                .mapToDouble(x -> x.getSubject().getEcts()).sum();

    }

}
