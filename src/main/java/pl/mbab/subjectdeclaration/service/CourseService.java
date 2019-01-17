package pl.mbab.subjectdeclaration.service;


import pl.mbab.subjectdeclaration.model.subject.Course;

import java.util.List;
import java.util.Set;

public interface CourseService {

    Set<Course> getAllCourses();
    Set<Course> findCourseBySignature();
    Set<Course> findCourseByName();
    Set<Course> findCourseByLecturerId();
    Set<Course> findCourseByLecturerName();
    Set<Course> findFieldCourses();
    Set<Course> addCourse(Long id);
    Set<Course> deleteCourse(Long id);
    Course findCourseById(Long id);
    double countEcts(Set<Course> courseSet);
    String findCoursebySubSignature(String signature, List<Course> courses);
}
