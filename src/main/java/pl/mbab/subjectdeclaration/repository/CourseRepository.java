package pl.mbab.subjectdeclaration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mbab.subjectdeclaration.model.subject.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findFirstById(Long id);
}
