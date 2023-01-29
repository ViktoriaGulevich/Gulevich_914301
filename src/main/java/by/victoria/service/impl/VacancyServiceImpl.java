package by.victoria.service.impl;

import by.victoria.exception.IncorrectDataException;
import by.victoria.exception.NoAccessException;
import by.victoria.exception.NotFoundException;
import by.victoria.model.entity.KeySkill;
import by.victoria.model.entity.User;
import by.victoria.model.entity.Vacancy;
import by.victoria.repository.VacancyRepository;
import by.victoria.service.KeySkillService;
import by.victoria.service.UserService;
import by.victoria.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;

    private final UserService userService;
    private final KeySkillService keySkillService;

    @Override
    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public Vacancy find(Long id) {
        return findById(id);
    }

    @Override
    public Set<User> findAllRespondUser() {
        return userService.getAuthenticated()
                .getVacancies()
                .stream()
                .findFirst()
                .orElse(new Vacancy())
                .getRespondUsers();
    }

    @Override
    public void add(Vacancy vacancy) {
        User user = userService.getAuthenticated();

        if (!user.getVacancies().isEmpty()) {
            throw new IncorrectDataException(String.format("User with id = %d already have vacancy", user.getId()));
        }

        Set<KeySkill> savedKeySkills = keySkillService.saveAll(vacancy.getSkills());
        vacancy.setSkills(savedKeySkills);

        vacancy = vacancyRepository.save(vacancy);
        user.addVacancy(vacancy);

        userService.save(user);
    }

    @Override
    public void update(Vacancy vacancy) {
        User user = userService.getAuthenticated();

        throwIfNoAccess(user, vacancy.getId());

        Set<KeySkill> savedKeySkills = keySkillService.saveAll(vacancy.getSkills());
        vacancy.setSkills(savedKeySkills);

        vacancyRepository.save(vacancy);
    }

    @Override
    public void delete(Long id) {
        User user = userService.getAuthenticated();

        throwIfNoAccess(user, id);

        vacancyRepository.deleteById(id);
    }

    @Override
    public void respond(Long id) {
        User user = userService.getAuthenticated();
        Vacancy vacancy = findById(id);

        vacancy.addRespondVacancy(user);

        vacancyRepository.save(vacancy);
    }

    private void throwIfNoAccess(User user, Long vacancyId) {
        boolean exist = userService.getAuthenticated()
                .getVacancies()
                .stream()
                .map(Vacancy::getId)
                .anyMatch(vacancyId::equals);

        if (!exist) {
            throw new NoAccessException(String.format("User with id = %d haven't access to vacancy with id = %d",
                    user.getId(),
                    vacancyId
            ));
        }
    }

    private Vacancy findById(Long id) {
        return vacancyRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Can't find vacancy use id = " + id)
        );
    }
}
