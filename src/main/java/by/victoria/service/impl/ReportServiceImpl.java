package by.victoria.service.impl;

import by.victoria.model.entity.Report;
import by.victoria.model.entity.Resume;
import by.victoria.model.entity.User;
import by.victoria.repository.ReportRepository;
import by.victoria.service.ReportService;
import by.victoria.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    private final UserService userService;

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public void saveAll(List<Resume> resumeList, String description) {
        User user = userService.getAuthenticated();

        List<Report> reports = resumeList
                .stream()
                .map(resume -> new Report(user, resume, description))
                .toList();

        reportRepository.saveAll(reports);
    }
}
