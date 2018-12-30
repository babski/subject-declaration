package pl.mbab.subjectdeclaration.model.student;

public enum Sex {
    MALE ("mężczyzna"),
    FEMALE ("kobieta");

    private String name;

    Sex(String name) {
        this.name = name;
    }
    public String getValue() {
        return name;
    }
}
