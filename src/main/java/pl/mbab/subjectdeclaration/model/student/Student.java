package pl.mbab.subjectdeclaration.model.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mbab.subjectdeclaration.model.subject.Course;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;

    @Enumerated(EnumType.STRING)
    private Sex sex;
    private int pesel;

    @Embedded
    private Address address;

    private int album;

    @ManyToOne
    @JoinColumn(name="field_id")
    private Field field;
    private int semester;
    private String email;
    private String login; // inicjały + nr indeksu - klucz główny
    private String password;


    @ManyToMany
    @JoinTable(name = "student_basket", joinColumns = @JoinColumn(name = "student_id"),
                inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Course> courseBasket;

}
