package by.victoria.service;

import by.victoria.model.entity.KeySkill;

import java.util.Set;

public interface KeySkillService {
    Set<KeySkill> saveAll(Set<KeySkill> skills);
}
