package pl.mbab.subjectdeclaration.repository;

import org.springframework.data.repository.CrudRepository;
import pl.mbab.subjectdeclaration.model.user.Field;

public interface FieldRepository extends CrudRepository<Field, Long> {
}
