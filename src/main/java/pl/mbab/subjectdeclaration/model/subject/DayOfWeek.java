package pl.mbab.subjectdeclaration.model.subject;

public enum DayOfWeek {
    MONDAY("poniedziałek"),

    TUESDAY("wtorek"),

    WEDNESDAY("środa"),

    THURSDAY("czwartek"),

    FRIDAY("piątek"),

    SATURDAY("sobota"),

    SUNDAY("niedziela");

    private String day;

    DayOfWeek(String day) {
        this.day = day;
    }

    public String getValue() {
        return day;
    }
}