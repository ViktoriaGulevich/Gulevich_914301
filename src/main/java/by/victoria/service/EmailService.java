package by.victoria.service;

import by.victoria.model.entity.Resume;
import by.victoria.model.entity.User;

import java.util.List;

public interface EmailService {
    void send(List<Resume> resumeList, String description);

    void send(User user);

    boolean checkCode(User user, String code);
}
