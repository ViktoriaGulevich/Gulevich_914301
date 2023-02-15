package by.victoria.data;

import by.victoria.model.dto.ResumeDto;
import by.victoria.model.entity.Resume;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeData {

    private static final String FIO = "ФИО";
    private static final String SEX = "М";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(2000, 1, 1);
    private static final String PROFESSION = "Профессия";
    private static final String POST = "Должность";
    private static final int WORK_EXPERIENCE = 24;
    private static final String TYPE_OF_EMPLOYMENT = "удаленно";
    private static final int SALARY = 1000;
    private static final int TIME_OF_EMPLOYMENT = 8;

    public static List<Resume> getResumeList() {
        return new ArrayList<>(List.of(getResume()));
    }

    public static Resume getResume() {
        Resume resume = new Resume();
        resume.setId(1L);
        resume.setFio(FIO);
        resume.setSex(SEX);
        resume.setDateOfBirth(DATE_OF_BIRTH);
        resume.setProfession(PROFESSION);
        resume.setPost(POST);
        resume.setWorkExperience(WORK_EXPERIENCE);
        resume.setTypeOfEmployment(TYPE_OF_EMPLOYMENT);
        resume.setSalary(SALARY);
        resume.setTimeOfEmployment(TIME_OF_EMPLOYMENT);
        return resume;
    }

    public static ResumeDto getResumeDto() {
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setFio(FIO);
        resumeDto.setSex(SEX);
        resumeDto.setDateOfBirth(DATE_OF_BIRTH);
        resumeDto.setProfession(PROFESSION);
        resumeDto.setPost(POST);
        resumeDto.setWorkExperience(WORK_EXPERIENCE);
        resumeDto.setTypeOfEmployment(TYPE_OF_EMPLOYMENT);
        resumeDto.setSalary(SALARY);
        resumeDto.setTimeOfEmployment(TIME_OF_EMPLOYMENT);
        return resumeDto;
    }

    public static List<ResumeDto> getResumeDtoList() {
        return List.of(getResumeDto());
    }
}
