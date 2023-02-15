package by.victoria.service.impl;

import by.victoria.data.KeySkillData;
import by.victoria.data.ResumeData;
import by.victoria.data.UserData;
import by.victoria.model.entity.Resume;
import by.victoria.model.entity.User;
import by.victoria.repository.ResumeRepository;
import by.victoria.service.KeySkillService;
import by.victoria.service.UserService;
import by.victoria.service.VacancyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeServiceTest {

    private final List<Resume> resumeList = ResumeData.getResumeList();
    private final User user = UserData.getUserEntity();
    private final Resume resume = ResumeData.getResume();

    @Mock
    private ResumeRepository resumeRepository;
    @Mock
    private UserService userService;
    @Mock
    private VacancyService vacancyService;
    @Mock
    private KeySkillService keySkillService;
    @InjectMocks
    private ResumeServiceImpl resumeService;

    @Test
    void findAll() {
        when(resumeRepository.findAll()).thenReturn(resumeList);

        List<Resume> actual = resumeService.findAll();

        assertEquals(resumeList, actual);
    }

    @Test
    void findFavorites() {
        user.setFavorites(new HashSet<>(resumeList));

        when(userService.getAuthenticated()).thenReturn(user);

        List<Resume> actual = resumeService.findFavorites();

        assertEquals(resumeList, actual);
    }

    @Test
    void findToSend() {
        user.setToSends(new HashSet<>(resumeList));

        when(userService.getAuthenticated()).thenReturn(user);

        List<Resume> actual = resumeService.findToSend();

        assertEquals(resumeList, actual);
    }

    @Test
    void findAllWithParams() {
        Predicate<Resume> filter = resume -> true;
        filter = filter.and(resume -> resume.getSalary() < 500);
        resume.setSalary(100);
        List<Resume> resumeList = List.of(resume);

        when(vacancyService.findAllRespondUser()).thenReturn(Set.of(user));
        when(resumeRepository.findAll()).thenReturn(resumeList);

        List<Resume> actual = resumeService.findAll(filter, false);

        assertEquals(resumeList, actual);
    }

    @Test
    void find() {
        Long id = 1L;

        when(resumeRepository.findById(id)).thenReturn(Optional.of(resume));

        Resume actual = resumeService.find(id);

        assertEquals(resume, actual);
    }

    @Test
    void add() {
        user.setResumes(new HashSet<>());
        resume.setSkills(KeySkillData.getSkills());

        when(userService.getAuthenticated()).thenReturn(user);
        when(keySkillService.saveAll(resume.getSkills())).thenReturn(resume.getSkills());
        when(resumeRepository.save(resume)).thenReturn(resume);
        doNothing().when(userService).save(user);

        resumeService.add(resume);

        assertTrue(user.getResumes().contains(resume));
        verify(userService).getAuthenticated();
        verify(keySkillService).saveAll(resume.getSkills());
        verify(resumeRepository).save(resume);
        verify(userService).save(user);
    }

    @Test
    void update() {
        user.setResumes(Set.of(resume));
        resume.setSkills(KeySkillData.getSkills());

        when(userService.getAuthenticated()).thenReturn(user);
        when(keySkillService.saveAll(resume.getSkills())).thenReturn(resume.getSkills());
        when(resumeRepository.save(resume)).thenReturn(resume);

        resumeService.update(resume);

        verify(userService).getAuthenticated();
        verify(keySkillService).saveAll(resume.getSkills());
        verify(resumeRepository).save(resume);
    }

    @Test
    void delete() {
        Long id = resume.getId();
        user.setResumes(Set.of(resume));

        when(userService.getAuthenticated()).thenReturn(user);
        doNothing().when(resumeRepository).deleteById(id);

        resumeService.delete(id);

        verify(userService).getAuthenticated();
        verify(resumeRepository).deleteById(id);
    }

    @Test
    void addToFavorites() {
        Long id = 1L;

        when(userService.getAuthenticated()).thenReturn(user);
        when(resumeRepository.findById(id)).thenReturn(Optional.of(resume));
        doNothing().when(userService).save(user);

        resumeService.addToFavorites(id);

        assertTrue(user.getFavorites().contains(resume));
        verify(userService).getAuthenticated();
        verify(resumeRepository).findById(id);
        verify(userService).save(user);
    }

    @Test
    void addToSend() {
        Long id = 1L;

        when(userService.getAuthenticated()).thenReturn(user);
        when(resumeRepository.findById(id)).thenReturn(Optional.of(resume));
        doNothing().when(userService).save(user);

        resumeService.addToSend(id);

        assertTrue(user.getToSends().contains(resume));
        verify(userService).getAuthenticated();
        verify(resumeRepository).findById(id);
        verify(userService).save(user);
    }

    @Test
    void deleteFromFavorites() {
        Long id = 1L;
        user.addResumeToFavorites(resume);

        when(userService.getAuthenticated()).thenReturn(user);
        when(resumeRepository.findById(id)).thenReturn(Optional.of(resume));
        doNothing().when(userService).save(user);

        resumeService.deleteFromFavorites(id);

        assertTrue(user.getFavorites().isEmpty());
        verify(userService).getAuthenticated();
        verify(resumeRepository).findById(id);
        verify(userService).save(user);
    }

    @Test
    void deleteFromSend() {
        Long id = 1L;
        user.addResumeToSend(resume);

        when(userService.getAuthenticated()).thenReturn(user);
        when(resumeRepository.findById(id)).thenReturn(Optional.of(resume));
        doNothing().when(userService).save(user);

        resumeService.deleteFromSend(id);

        assertTrue(user.getToSends().isEmpty());
        verify(userService).getAuthenticated();
        verify(resumeRepository).findById(id);
        verify(userService).save(user);
    }

    @Test
    void deleteAllFromSend() {
        user.addResumeToSend(resume);

        when(userService.getAuthenticated()).thenReturn(user);
        doNothing().when(userService).save(user);

        resumeService.deleteAllFromSend();

        assertTrue(user.getToSends().isEmpty());
        verify(userService).getAuthenticated();
        verify(userService).save(user);
    }
}
