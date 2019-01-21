package pl.mbab.subjectdeclaration.service;

import org.springframework.stereotype.Service;
import pl.mbab.subjectdeclaration.model.user.Field;
import pl.mbab.subjectdeclaration.repository.FieldRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    private FieldRepository fieldRepository;

    public FieldServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public List<Field> getAllFields() {
        List<Field> result = new ArrayList<>();
        fieldRepository.findAll().iterator().forEachRemaining(result::add);
        return result;
    }
}