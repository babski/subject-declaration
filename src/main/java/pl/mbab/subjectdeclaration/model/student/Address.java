package pl.mbab.subjectdeclaration.model.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Setter
@Getter
@NoArgsConstructor
@Embeddable
public class Address {

    private String street;
    private String localNumber;
    private String zipCode;
    private String city;
}
