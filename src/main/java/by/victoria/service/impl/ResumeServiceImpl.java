package by.victoria.service.impl;

import by.victoria.exception.IncorrectDataException;
import by.victoria.exception.NoAccessException;
import by.victoria.exception.NotFoundException;
import by.victoria.model.entity.KeySkill;
import by.victoria.model.entity.Resume;
import by.victoria.model.entity.User;
import by.victoria.repository.ResumeRepository;
import by.victoria.service.KeySkillService;
import by.victoria.service.ResumeService;
import by.victoria.service.UserService;
import by.victoria.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    private final UserService userService;
    private final VacancyService vacancyService;
    private final KeySkillService keySkillService;


    @Override
    public List<Resume> findAll() {
        return resumeRepository.findAll();
    }

    @Override
    public List<Resume> findFavorites() {
        return userService.getAuthenticated()
                .getFavorites()
                .stream()
                .toList();
    }

    @Override
    public List<Resume> findToSend() {
        return userService.getAuthenticated()
                .getToSends()
                .stream()
                .toList();
    }

    @Override
    public List<Resume> findAll(Predicate<Resume> filter, Boolean isRespond) {
        Set<Resume> resumes = vacancyService.findAllRespondUser()
                .stream()
                .flatMap(user -> user.getResumes().stream())
                .collect(Collectors.toSet());

        filter = filter.and(resume -> {
            if (isRespond == null || !isRespond) {
                return true;
            }
            return resumes.contains(resume);
        });

        return resumeRepository.findAll()
                .stream()
                .filter(filter)
                .toList();
    }

    @Override
    public Resume find(Long id) {
        return findById(id);
    }

    @Override
    public void add(Resume resume) {
        User user = userService.getAuthenticated();

        if (!user.getResumes().isEmpty()) {
            throw new IncorrectDataException(String.format("User with id = %d already have resume", user.getId()));
        }

        Set<KeySkill> savedKeySkills = keySkillService.saveAll(resume.getSkills());
        resume.setSkills(savedKeySkills);

        resume = resumeRepository.save(resume);
        user.addResume(resume);
        userService.save(user);
    }

    @Override
    public void update(Resume resume) {
        User user = userService.getAuthenticated();

        throwIfNoAccess(user, resume.getId());

        Set<KeySkill> savedKeySkills = keySkillService.saveAll(resume.getSkills());
        resume.setSkills(savedKeySkills);

        resumeRepository.save(resume);
    }

    @Override
    public void delete(Long id) {
        User user = userService.getAuthenticated();

        throwIfNoAccess(user, id);

        resumeRepository.deleteById(id);
    }

    @Override
    public void addToFavorites(Long id) {
        User user = userService.getAuthenticated();
        Resume resume = findById(id);

        user.addResumeToFavorites(resume);

        userService.save(user);
    }

    @Override
    public void addToSend(Long id) {
        User user = userService.getAuthenticated();
        Resume resume = findById(id);

        user.addResumeToSend(resume);

        userService.save(user);
    }

    @Override
    public void deleteFromFavorites(Long id) {
        User user = userService.getAuthenticated();
        Resume resume = findById(id);

        user.deleteFromFavorites(resume);

        userService.save(user);
    }

    @Override
    public void deleteFromSend(Long id) {
        User user = userService.getAuthenticated();
        Resume resume = findById(id);

        user.deleteFromSend(resume);

        userService.save(user);
    }

    @Override
    public void deleteAllFromSend() {
        User user = userService.getAuthenticated();

        user.getToSends().clear();

        userService.save(user);
    }

    private void throwIfNoAccess(User user, Long resumeId) {
        boolean exist = userService.getAuthenticated()
                .getResumes()
                .stream()
                .map(Resume::getId)
                .anyMatch(resumeId::equals);

        if (!exist) {
            throw new NoAccessException(String.format("User with id = %d haven't access to resume with id = %d",
                    user.getId(),
                    resumeId
            ));
        }
    }

    private Resume findById(Long id) {
        return resumeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Can't find resume use id = " + id)
        );
    }
}
