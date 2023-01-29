package by.victoria.controller;

import by.victoria.mapper.ReportMapper;
import by.victoria.model.dto.ReportDto;
import by.victoria.model.entity.Report;
import by.victoria.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    private final ReportMapper reportMapper;

    @Secured({"ROLE_RECRUITER"})
    @GetMapping
    public List<ReportDto> findAll() {
        List<Report> reportList = reportService.findAll();

        return reportMapper.toListDto(reportList);
    }
}
