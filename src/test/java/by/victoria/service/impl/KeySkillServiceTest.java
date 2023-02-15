package by.victoria.service.impl;

import by.victoria.model.entity.KeySkill;
import by.victoria.repository.KeySkillRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KeySkillServiceTest {

    @Mock
    private KeySkillRepository keySkillRepository;
    @InjectMocks
    private KeySkillServiceImpl keySkillService;

    @Test
    void saveAll() {
        String firstName = "FIRST";
        String secondName = "SECOND";
        KeySkill firstSkill = new KeySkill(firstName);
        KeySkill secondSkill = new KeySkill(secondName);
        Set<KeySkill> keySkills = Set.of(firstSkill, secondSkill);
        KeySkill expectedFirstSkill = new KeySkill() {{
            setId(1L);
            setName(firstName);
        }};
        KeySkill expectedSecondSkill = new KeySkill() {{
            setId(2L);
            setName(secondName);
        }};
        Set<KeySkill> expected = Set.of(expectedFirstSkill, expectedSecondSkill);

        when(keySkillRepository.findByName(firstName)).thenReturn(Optional.of(expectedFirstSkill));
        when(keySkillRepository.findByName(secondName)).thenReturn(Optional.empty());
        when(keySkillRepository.save(secondSkill)).thenReturn(expectedSecondSkill);

        Set<KeySkill> actual = keySkillService.saveAll(keySkills);

        assertEquals(expected, actual);
    }
}
