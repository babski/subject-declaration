package pl.mbab.subjectdeclaration.model.user;

public enum Semester {
    I(new String[]{"210100", "210110"}, 16.5, 0.0),
    II(new String[]{}, 12.0, 12.0),
    III(new String[]{}, 12.0, 9.0),
    IV(new String[]{}, 0.0, 9.0);

    private String[] compulsorySubjects;
    private double field1Ects;
    private double field2Ects;
    public final static double ectsMin = 30.0;
    public final static double ectsMax = 70.0;

    Semester(String[] compulsorySubjects, double field1Ects, double field2Ects) {
        this.compulsorySubjects = compulsorySubjects;
        this.field1Ects = field1Ects;
        this.field2Ects = field2Ects;
    }

    public String[] getCompulsorySubjects() {
        return compulsorySubjects;
    }

    public double getField1Ects() {
        return field1Ects;
    }

    public double getField2Ects() {
        return field2Ects;
    }
}
