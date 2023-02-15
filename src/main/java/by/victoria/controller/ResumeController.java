package by.victoria.controller;

import by.victoria.mapper.FilterMapper;
import by.victoria.mapper.ResumeMapper;
import by.victoria.model.dto.FilterDto;
import by.victoria.model.dto.ResumeDto;
import by.victoria.model.entity.Resume;
import by.victoria.service.EmailService;
import by.victoria.service.ReportService;
import by.victoria.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {
    private final ResumeService resumeService;
    private final EmailService emailService;
    private final ReportService reportService;

    private final FilterMapper filterMapper;
    private final ResumeMapper resumeMapper;


    @Secured({"ROLE_RECRUITER"})
    @GetMapping("/all")
    public List<ResumeDto> findAll() {
        List<Resume> resumeList = resumeService.findAll();

        return resumeMapper.toResumeDtoList(resumeList);
    }

    @Secured({"ROLE_RECRUITER"})
    @PostMapping("/filter")
    public List<ResumeDto> findAllByFilter(@RequestBody FilterDto filterDto) {
        List<Resume> resumeList = resumeService.findAll(filterMapper.toFilter(filterDto), filterDto.getIsRespond());

        return resumeMapper.toResumeDtoList(resumeList);
    }

    @Secured({"ROLE_RECRUITER"})
    @GetMapping("/favorites")
    public List<ResumeDto> findFavorites() {
        List<Resume> resumeList = resumeService.findFavorites();

        return resumeMapper.toResumeDtoList(resumeList);
    }

    @Secured({"ROLE_RECRUITER"})
    @PostMapping("/favorites")
    public void addToFavorites(@RequestBody Long id) {
        resumeService.addToFavorites(id);
    }

    @Secured({"ROLE_RECRUITER"})
    @PatchMapping("/favorites")
    public void deleteFromFavorites(@RequestBody Long id) {
        resumeService.deleteFromFavorites(id);
    }

    @Secured({"ROLE_RECRUITER"})
    @GetMapping("/to-send")
    public List<ResumeDto> findToSend() {
        List<Resume> toSend = resumeService.findToSend();

        return resumeMapper.toResumeDtoList(toSend);
    }

    @Secured({"ROLE_RECRUITER"})
    @PostMapping("/to-send")
    public void addToSend(@RequestBody Long id) {
        resumeService.addToSend(id);
    }

    @Secured({"ROLE_RECRUITER"})
    @PatchMapping("/to-send")
    public void deleteFromSend(@RequestBody Long id) {
        resumeService.deleteFromSend(id);
    }

    @Secured({"ROLE_RECRUITER", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResumeDto find(@PathVariable Long id) {
        Resume resume = resumeService.find(id);

        return resumeMapper.toResumeDto(resume);
    }

    @Secured({"ROLE_USER"})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void add(@Valid @RequestBody ResumeDto resumeDto) {
        Resume resume = resumeMapper.toResume(resumeDto);

        resumeService.add(resume);
    }

    @Secured({"ROLE_USER"})
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    @PatchMapping
    public void update(@Valid @RequestBody ResumeDto resumeDto) {
        Resume resume = resumeMapper.toResume(resumeDto);

        resumeService.update(resume);
    }

    @Secured({"ROLE_USER"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        resumeService.delete(id);
    }

    @Secured({"ROLE_RECRUITER"})
    @PostMapping("/send")
    public void send(@RequestBody String description) {
        List<Resume> resumeList = resumeService.findToSend();
        emailService.send(resumeList, description);

        reportService.saveAll(resumeList, description);

        resumeService.deleteAllFromSend();
    }
}
