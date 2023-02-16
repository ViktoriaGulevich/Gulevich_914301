package by.victoria.data;

import by.victoria.model.dto.VacancyDto;
import by.victoria.model.entity.Vacancy;

import java.util.List;

public class VacancyData {

    private static final String PROFESSION = "Профессия";
    private static final String POST = "Должность";
    private static final int WORK_EXPERIENCE = 24;
    private static final int TIME_OF_EMPLOYMENT = 8;
    private static final int SALARY = 1000;

    public static Vacancy getVacancy() {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(1L);
        vacancy.setProfession(PROFESSION);
        vacancy.setPost(POST);
        vacancy.setWorkExperience(WORK_EXPERIENCE);
        vacancy.setTimeOfEmployment(TIME_OF_EMPLOYMENT);
        vacancy.setSalary(SALARY);
        return vacancy;
    }

    public static List<Vacancy> getVacancyList() {
        return List.of(getVacancy());
    }

    public static VacancyDto getVacancyDto() {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setProfession(PROFESSION);
        vacancyDto.setPost(POST);
        vacancyDto.setWorkExperience(WORK_EXPERIENCE);
        vacancyDto.setTimeOfEmployment(TIME_OF_EMPLOYMENT);
        vacancyDto.setSalary(SALARY);
        return vacancyDto;
    }

    public static List<VacancyDto> getVacancyDtoList() {
        return List.of(getVacancyDto());
    }
}
