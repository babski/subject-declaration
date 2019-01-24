package pl.mbab.subjectdeclaration.service;

import pl.mbab.subjectdeclaration.model.subject.FieldSubject;
import pl.mbab.subjectdeclaration.model.user.Field;

import java.util.List;

public interface FieldService {

    List<Field> getAllFields();

    String getFieldNameById(Long id);

    List<FieldSubject> getFieldListById(Long id);
}
