package pl.mbab.subjectdeclaration.service;

public interface EmailService {
    public void sendConfirmationMessage(String to, String subject, String text);
}
