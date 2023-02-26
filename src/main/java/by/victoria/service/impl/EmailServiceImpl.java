package by.victoria.service.impl;

import by.victoria.model.entity.Resume;
import by.victoria.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.message.address.from}")
    private String emailAddressFrom;
    //delete it when email confirmation will work
    @Value("${spring.mail.message.address.to}")
    private String emailAddressTo;
    @Value("${spring.mail.message.subject}")
    private String subject;

    @Override
    public void send(List<Resume> resumeList, String description) {
        resumeList.stream()
                .map(resume -> {
                    SimpleMailMessage message = new SimpleMailMessage();

                    //use authUser.getEmail()
                    message.setFrom(emailAddressFrom);
                    //use resume.getUsers().findFirst().get().getEmail()
                    message.setTo(emailAddressTo);
                    message.setSubject(subject);
                    message.setText(description);

                    return message;
                })
                .forEach(simpleMessage -> emailSender.send(simpleMessage));
    }
}
