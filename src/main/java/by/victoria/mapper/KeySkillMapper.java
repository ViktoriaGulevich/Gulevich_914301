package by.victoria.mapper;

import by.victoria.model.entity.KeySkill;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface KeySkillMapper {
    String SEPARATOR = ", ";

    default Set<KeySkill> toKeySkillSet(String skills) {
        return Arrays.stream(skills.split(SEPARATOR))
                .map(KeySkill::new)
                .collect(Collectors.toSet());
    }

    default String toKeySkillString(Set<KeySkill> skills) {
        return skills.stream()
                .map(KeySkill::getName)
                .collect(Collectors.joining(SEPARATOR));
    }
}
