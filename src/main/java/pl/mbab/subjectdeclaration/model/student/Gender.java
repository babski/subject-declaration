package pl.mbab.subjectdeclaration.model.student;

public enum Gender {
    FEMALE ("kobieta"),
    MALE ("mężczyzna");

    private String name;

    Gender(String name) {
        this.name = name;
    }
    public String getValue() {
        return name;
    }
}
