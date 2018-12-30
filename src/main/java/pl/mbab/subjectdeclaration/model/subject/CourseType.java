package pl.mbab.subjectdeclaration.model.subject;

public enum CourseType {
    LECTURE ("wykład"),

    CLASSES ("ćwiczenia");

    private String type;

    CourseType(String type) {
        this.type = type;
    }

    public String getValue() {
        return type;
    }
}
