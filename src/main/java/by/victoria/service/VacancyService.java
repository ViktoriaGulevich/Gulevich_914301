package by.victoria.service;

import by.victoria.model.entity.User;
import by.victoria.model.entity.Vacancy;

import java.util.List;
import java.util.Set;

public interface VacancyService {
    List<Vacancy> findAll();

    Vacancy find(Long id);

    Set<User> findAllRespondUser();

    void add(Vacancy vacancy);

    void update(Vacancy vacancy);

    void delete(Long id);

    void respond(Long id);
}
