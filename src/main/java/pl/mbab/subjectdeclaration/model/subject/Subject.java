package pl.mbab.subjectdeclaration.model.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subject {

    @Id
    @Column(length = 100)
    private String signature;
    private String name;
    private double ects;

    public String getSignature() {
        return signature;
    }

    public String getName() {
        return name;
    }

}
