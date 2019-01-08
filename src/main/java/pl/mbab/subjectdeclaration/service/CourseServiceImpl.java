package pl.mbab.subjectdeclaration.service;

import org.springframework.stereotype.Service;
import pl.mbab.subjectdeclaration.model.subject.Course;
import pl.mbab.subjectdeclaration.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        List<Course> result = new ArrayList<>();
        courseRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }

    @Override
    public List<Course> findCourseBySignature() {
        return null;
    }

    @Override
    public List<Course> findCourseByName() {
        return null;
    }

    @Override
    public List<Course> findCourseByLecturerId() {
        return null;
    }

    @Override
    public List<Course> findCourseByLecturerName() {
        return null;
    }

    @Override
    public List<Course> findFieldCourses() {
        return null;
    }

    @Override
    public List<Course> addCourse(Long id) {
        return null;
    }

    @Override
    public List<Course> deleteCourse(Long id) {
        return null;
    }

    @Override
    public Course findCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
            }
}
