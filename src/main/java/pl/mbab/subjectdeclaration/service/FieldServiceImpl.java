package pl.mbab.subjectdeclaration.service;

import org.springframework.stereotype.Service;
import pl.mbab.subjectdeclaration.model.student.Field;
import pl.mbab.subjectdeclaration.repository.FieldRepository;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class FieldServiceImpl implements FieldService {

    private FieldRepository fieldRepository;

    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Set<Field> getAllFields() {
        Set<Field> result = new LinkedHashSet<>();
        fieldRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}
