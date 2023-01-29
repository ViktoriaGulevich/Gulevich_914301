package by.victoria.mapper;

import by.victoria.model.dto.FilterDto;
import by.victoria.model.entity.KeySkill;
import by.victoria.model.entity.Resume;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class FilterMapper {
    @Autowired
    private KeySkillMapper keySkillMapper;

    public Predicate<Resume> toFilter(FilterDto filterDto) {
        Predicate<Resume> filter = new Predicate<Resume>() {
            @Override
            public boolean test(Resume resume) {
                return true;
            }
        };
        if (filterDto == null) {
            return filter;
        }
        return filter
                .and(resume -> sexCheck(resume, filterDto))
                .and(resume -> filterDto.getAgeFrom() == null || resume.getAge() >= filterDto.getAgeFrom())
                .and(resume -> filterDto.getAgeTo() == null || resume.getAge() <= filterDto.getAgeTo())
                .and(resume -> filterDto.getWorkExperience() == null
                        || resume.getWorkExperience() >= filterDto.getWorkExperience()
                )
                .and(resume -> filterDto.getTimeOfEmployment() == null
                        || resume.getTimeOfEmployment() <= filterDto.getTimeOfEmployment()
                )
                .and(resume -> filterDto.getTypeOfEmployment() == null
                        || filterDto.getTypeOfEmployment().isBlank()
                        || resume.getTypeOfEmployment().equalsIgnoreCase(filterDto.getTypeOfEmployment())
                )
                .and(resume -> filterDto.getSalary() == null || resume.getSalary() <= filterDto.getSalary())
                .and(resume -> filterDto.getProfession() == null
                        || filterDto.getProfession().isBlank()
                        || resume.getProfession().toLowerCase().contains(filterDto.getProfession().toLowerCase())
                )
                .and(resume -> filterDto.getPost() == null
                        || filterDto.getPost().isBlank()
                        || resume.getPost().equalsIgnoreCase(filterDto.getPost()))
                .and(resume -> isSkillsCheckOrTrue(resume, filterDto));
    }

    private boolean sexCheck(Resume resume, FilterDto filterDto) {

        if (isFalse(filterDto.getSexM()) && isFalse(filterDto.getSexW())) {
            return true;
        }
        if (isTrue(filterDto.getSexM()) && isTrue(filterDto.getSexW())) {
            return true;
        }
        if (isTrue(filterDto.getSexM()) && isFalse(filterDto.getSexW())) {
            return resume.getSex().equalsIgnoreCase("М");
        }
        if (isFalse(filterDto.getSexM()) && isTrue(filterDto.getSexW())) {
            return resume.getSex().equalsIgnoreCase("Ж");
        }
        return false;
    }

    private boolean isTrue(Boolean b) {
        return b != null && b;
    }

    private boolean isFalse(Boolean b) {
        return b == null || !b;
    }

    private boolean isSkillsCheckOrTrue(Resume resume, FilterDto filterDto) {
        if (filterDto.getSkills() == null || filterDto.getSkills().isBlank()) {
            return true;
        }

        Set<String> filterDtoSkills = keySkillMapper.toKeySkillSet(filterDto.getSkills())
                .stream()
                .map(KeySkill::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        return resume.getSkills()
                .stream()
                .map(KeySkill::getName)
                .collect(Collectors.toSet())
                .containsAll(filterDtoSkills);
    }
}