package by.victoria.mapper;

import by.victoria.model.dto.ReportDto;
import by.victoria.model.entity.Report;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    @Mapping(target = "login", source = "report.user.login")
    @Mapping(target = "resumeId", source = "report.resume.id")
    @Mapping(target = "fio", source = "report.resume.fio")
    @Mapping(target = "description", source = "report.message")
    ReportDto toDto(Report report);

    List<ReportDto> toListDto(List<Report> reportList);
}
