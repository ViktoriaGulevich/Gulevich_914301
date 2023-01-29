package by.victoria.mapper;

import by.victoria.model.dto.VacancyDto;
import by.victoria.model.entity.Vacancy;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeySkillMapper.class})
public interface VacancyMapper {
    Vacancy toVacancy(VacancyDto vacancyDto);

    VacancyDto toVacancyDto(Vacancy vacancy);

    List<VacancyDto> toVacancyDtoList(List<Vacancy> vacancyList);
}
