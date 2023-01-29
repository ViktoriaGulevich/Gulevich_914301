package by.victoria.service;

import by.victoria.model.entity.Resume;

import java.util.List;

public interface EmailService {
    void send(List<Resume> resumeList, String description);
}
