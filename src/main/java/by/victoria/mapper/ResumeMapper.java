package by.victoria.mapper;

import by.victoria.model.dto.ResumeDto;
import by.victoria.model.entity.Resume;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KeySkillMapper.class})
public interface ResumeMapper {
    Resume toResume(ResumeDto resumeDto);

    ResumeDto toResumeDto(Resume resume);

    List<ResumeDto> toResumeDtoList(List<Resume> resumeList);
}
