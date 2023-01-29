package by.victoria.service.impl;

import by.victoria.model.entity.KeySkill;
import by.victoria.repository.KeySkillRepository;
import by.victoria.service.KeySkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeySkillServiceImpl implements KeySkillService {
    private final KeySkillRepository keySkillRepository;


    @Override
    public Set<KeySkill> saveAll(Set<KeySkill> skills) {
        return skills.stream()
                .map(keySkill -> saveIfNeedAndGet(keySkill.getName()))
                .collect(Collectors.toSet());
    }

    private KeySkill saveIfNeedAndGet(String name) {
        String upperName = name.toUpperCase();

        return keySkillRepository.findByName(upperName).orElseGet(() ->
                keySkillRepository.save(new KeySkill(upperName))
        );
    }
}
