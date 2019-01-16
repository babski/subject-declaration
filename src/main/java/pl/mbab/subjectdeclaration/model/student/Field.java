package pl.mbab.subjectdeclaration.model.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mbab.subjectdeclaration.model.subject.FieldSubject;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "field")
    @Column(name = "field_subject_list")
    private Set<FieldSubject> fieldSubjects;

}
