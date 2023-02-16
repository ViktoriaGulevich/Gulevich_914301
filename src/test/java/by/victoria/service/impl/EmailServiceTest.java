package by.victoria.service.impl;

import by.victoria.data.ResumeData;
import by.victoria.model.entity.Resume;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;
    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void send() {
        String message = "message";
        List<Resume> resumeList = ResumeData.getResumeList();
        resumeList.add(ResumeData.getResume());
        resumeList.add(ResumeData.getResume());
        resumeList.add(ResumeData.getResume());

        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        emailService.send(resumeList, message);

        verify(emailSender, times(resumeList.size())).send(any(SimpleMailMessage.class));
    }
}