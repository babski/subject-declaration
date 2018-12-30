package pl.mbab.subjectdeclaration.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mbab.subjectdeclaration.model.subject.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
