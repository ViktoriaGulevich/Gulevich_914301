package by.victoria.service;

import by.victoria.model.entity.Report;
import by.victoria.model.entity.Resume;

import java.util.List;

public interface ReportService {
    List<Report> findAll();

    void saveAll(List<Resume> resumeList, String description);
}
