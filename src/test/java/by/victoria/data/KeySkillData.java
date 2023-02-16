package by.victoria.data;

import by.victoria.model.entity.KeySkill;

import java.util.Set;

public class KeySkillData {
    public static Set<KeySkill> getSkills() {
        KeySkill first = new KeySkill() {{
            setId(1L);
            setName("FIRST");
        }};
        KeySkill second = new KeySkill() {{
            setId(2L);
            setName("SECOND");
        }};
        return Set.of(first, second);
    }
}
