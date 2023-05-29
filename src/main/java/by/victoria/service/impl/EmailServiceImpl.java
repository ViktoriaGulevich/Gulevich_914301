package by.victoria.service.impl;

import by.victoria.model.entity.Resume;
import by.victoria.model.entity.User;
import by.victoria.service.EmailService;
import by.victoria.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final UserService userService;
    @Value("${spring.mail.message.address.from}")
    private String emailAddressFrom;
    @Value("${spring.mail.message.subject}")
    private String subject;
    private static final Map<User, String> codeMap = new HashMap<>();

    @Override
    public void send(List<Resume> resumeList, String description) {
        resumeList.stream()
                .map(resume -> createMessage(userService.getUserByResumeId(resume.getId()).getEmail(), subject, description))
                .forEach(emailSender::send);
    }

    @Override
    public void send(User user) {
        String code = generateCode();
        codeMap.put(user, code);
        SimpleMailMessage message = createMessage(user.getEmail(), "", code);
        emailSender.send(message);
    }

    @Override
    public boolean checkCode(User user, String code) {
        return Optional.ofNullable(codeMap.get(user))
                .map(c -> c.equals(code))
                .orElse(false);
    }

    private SimpleMailMessage createMessage(String to, String subject, String mailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailAddressFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(mailMessage);
        return message;
    }

    private String generateCode() {
        return Integer.toString((int) (Math.random() * 10000) + 1000);
    }
}
