package pl.mbab.subjectdeclaration.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mbab.subjectdeclaration.model.subject.Course;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 100)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(length = 100)
    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String pesel;
    @Enumerated(EnumType.STRING)
    private Semester semester;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_basket", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Course> courseBasket = new ArrayList<>();
    private boolean basketAccepted;
    @Enumerated
    private UserStatus userStatus;
    private String token;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
}