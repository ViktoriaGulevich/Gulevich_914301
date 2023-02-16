package by.victoria.service.impl;

import by.victoria.data.ResumeData;
import by.victoria.data.UserData;
import by.victoria.model.entity.Report;
import by.victoria.model.entity.Resume;
import by.victoria.model.entity.User;
import by.victoria.repository.ReportRepository;
import by.victoria.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void findAll() {
        List<Report> expected = List.of(new Report() {{
            setId(1L);
        }});

        when(reportRepository.findAll()).thenReturn(expected);

        List<Report> actual = reportService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    void saveAll() {
        User user = UserData.getUserEntity();
        Resume resume = ResumeData.getResume();
        String message = "message";
        List<Report> reportList = List.of(new Report(user, resume, message));

        when(userService.getAuthenticated()).thenReturn(user);
        when(reportRepository.saveAll(reportList)).thenReturn(reportList);

        reportService.saveAll(ResumeData.getResumeList(), message);

        verify(userService).getAuthenticated();
        verify(reportRepository).saveAll(reportList);
    }
}
