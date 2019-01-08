package pl.mbab.subjectdeclaration.model.student;

public enum Gender {
    MALE ("mężczyzna"),
    FEMALE ("kobieta");

    private String name;

    Gender(String name) {
        this.name = name;
    }
    public String getValue() {
        return name;
    }
}
