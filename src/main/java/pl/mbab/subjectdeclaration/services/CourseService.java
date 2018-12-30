package pl.mbab.subjectdeclaration.services;



import pl.mbab.subjectdeclaration.model.subject.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();
    List<Course> findCourseBySignature();
    List<Course> findCourseByName();
    List<Course> findCourseByLecturerId();
    List<Course> findCourseByLecturerName();
    List<Course> findFieldCourses();
    List<Course> addCourse(Long id);
    List<Course> deleteCourse(Long id);
}
