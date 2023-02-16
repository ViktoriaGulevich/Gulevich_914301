package by.victoria.service.impl;

import by.victoria.data.KeySkillData;
import by.victoria.data.UserData;
import by.victoria.data.VacancyData;
import by.victoria.model.entity.User;
import by.victoria.model.entity.Vacancy;
import by.victoria.repository.VacancyRepository;
import by.victoria.service.KeySkillService;
import by.victoria.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VacancyServiceTest {

    private static final Long ID = 1L;
    private final List<Vacancy> vacancyList = VacancyData.getVacancyList();
    private final User user = UserData.getUserEntity();
    private final Vacancy vacancy = VacancyData.getVacancy();
    @Mock
    private VacancyRepository vacancyRepository;
    @Mock
    private UserService userService;
    @Mock
    private KeySkillService keySkillService;
    @InjectMocks
    private VacancyServiceImpl vacancyService;

    @Test
    void findAll() {
        when(vacancyRepository.findAll()).thenReturn(vacancyList);

        List<Vacancy> actual = vacancyService.findAll();

        assertEquals(vacancyList, actual);
        verify(vacancyRepository).findAll();
    }

    @Test
    void find() {
        when(vacancyRepository.findById(ID)).thenReturn(Optional.of(vacancy));

        Vacancy actual = vacancyService.find(ID);

        assertEquals(vacancy, actual);
        verify(vacancyRepository).findById(ID);
    }

    @Test
    void findAllRespondUser() {
        User respondUser = new User();
        Set<User> expected = Set.of(respondUser);
        vacancy.setRespondUsers(expected);
        Set<Vacancy> vacancySet = Set.of(vacancy);
        user.setVacancies(vacancySet);

        when(userService.getAuthenticated()).thenReturn(user);

        Set<User> actual = vacancyService.findAllRespondUser();

        assertEquals(expected, actual);
    }

    @Test
    void add() {
        user.setVacancies(new HashSet<>());
        vacancy.setSkills(KeySkillData.getSkills());

        when(userService.getAuthenticated()).thenReturn(user);
        when(keySkillService.saveAll(vacancy.getSkills())).thenReturn(vacancy.getSkills());
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);
        doNothing().when(userService).save(user);

        vacancyService.add(vacancy);

        assertTrue(user.getVacancies().contains(vacancy));
        verify(userService).getAuthenticated();
        verify(keySkillService).saveAll(vacancy.getSkills());
        verify(vacancyRepository).save(vacancy);
        verify(userService).save(user);
    }

    @Test
    void update() {
        user.setVacancies(Set.of(vacancy));
        vacancy.setSkills(KeySkillData.getSkills());

        when(userService.getAuthenticated()).thenReturn(user);
        when(keySkillService.saveAll(vacancy.getSkills())).thenReturn(vacancy.getSkills());
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);

        vacancyService.update(vacancy);

        verify(userService).getAuthenticated();
        verify(keySkillService).saveAll(vacancy.getSkills());
        verify(vacancyRepository).save(vacancy);
    }

    @Test
    void delete() {
        user.setVacancies(Set.of(vacancy));

        when(userService.getAuthenticated()).thenReturn(user);
        doNothing().when(vacancyRepository).deleteById(ID);

        vacancyService.delete(ID);

        verify(userService).getAuthenticated();
        verify(vacancyRepository).deleteById(ID);
    }

    @Test
    void respond() {
        when(userService.getAuthenticated()).thenReturn(user);
        when(vacancyRepository.findById(ID)).thenReturn(Optional.of(vacancy));
        when(vacancyRepository.save(vacancy)).thenReturn(vacancy);

        vacancyService.respond(ID);

        assertTrue(vacancy.getRespondUsers().contains(user));
        verify(userService).getAuthenticated();
        verify(vacancyRepository).findById(ID);
        verify(vacancyRepository).save(vacancy);
    }
}
