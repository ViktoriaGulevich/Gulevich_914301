package by.victoria.service;

import by.victoria.model.entity.Resume;

import java.util.List;
import java.util.function.Predicate;

public interface ResumeService {
    List<Resume> findAll();

    List<Resume> findFavorites();

    List<Resume> findToSend();

    List<Resume> findAll(Predicate<Resume> filter, Boolean isRespond);

    Resume find(Long id);

    void add(Resume resume);

    void update(Resume resume);

    void delete(Long id);

    void addToFavorites(Long id);

    void addToSend(Long id);

    void deleteFromFavorites(Long id);

    void deleteFromSend(Long id);

    void deleteAllFromSend();
}
