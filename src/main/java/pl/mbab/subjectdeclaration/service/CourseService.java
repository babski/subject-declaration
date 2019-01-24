package pl.mbab.subjectdeclaration.service;

import pl.mbab.subjectdeclaration.model.subject.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    List<Course> getFieldCourses(String email, boolean group1);

    Course findCourseById(Long id);

    double countEcts(List<Course> courseSet);

    String findCoursebySubSignature(String signature, List<Course> courses);

    void addCourse(String email, Long courseId);

    void deleteCourse(String email, Long courseId);

    boolean validate(String email);
}