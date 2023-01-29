package by.victoria.controller;

import by.victoria.mapper.VacancyMapper;
import by.victoria.model.dto.VacancyDto;
import by.victoria.model.entity.Vacancy;
import by.victoria.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancy")
public class VacancyController {
    private final VacancyService vacancyService;

    private final VacancyMapper vacancyMapper;


    @Secured({"ROLE_USER"})
    @GetMapping
    public List<VacancyDto> findAll() {
        List<Vacancy> vacancyList = vacancyService.findAll();

        return vacancyMapper.toVacancyDtoList(vacancyList);
    }

    @Secured({"ROLE_RECRUITER", "ROLE_USER"})
    @GetMapping("/{id}")
    public VacancyDto find(@PathVariable Long id) {
        Vacancy vacancy = vacancyService.find(id);

        return vacancyMapper.toVacancyDto(vacancy);
    }

    @Secured({"ROLE_RECRUITER"})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void add(@Valid @RequestBody VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyMapper.toVacancy(vacancyDto);

        vacancyService.add(vacancy);
    }

    @Secured({"ROLE_RECRUITER"})
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @PatchMapping
    public void update(@Valid @RequestBody VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyMapper.toVacancy(vacancyDto);

        vacancyService.update(vacancy);
    }

    @Secured({"ROLE_RECRUITER"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vacancyService.delete(id);
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/respond")
    public void respond(@RequestBody Long id) {
        vacancyService.respond(id);
    }
}
