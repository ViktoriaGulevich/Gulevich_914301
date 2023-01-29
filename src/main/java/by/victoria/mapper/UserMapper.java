package by.victoria.mapper;

import by.victoria.exception.NotFoundException;
import by.victoria.model.dto.UserDto;
import by.victoria.model.entity.Resume;
import by.victoria.model.entity.Role;
import by.victoria.model.entity.User;
import by.victoria.model.entity.Vacancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Named("roleToString")
    static String roleToString(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("There are not roles"));
    }

    @Named("resumeToLong")
    static Long resumeToLong(Set<Resume> resumes) {
        return resumes.stream()
                .map(Resume::getId)
                .findFirst()
                .orElse(0L);
    }

    @Named("vacancyToLong")
    static Long vacancyToLong(Set<Vacancy> vacancies) {
        return vacancies.stream()
                .map(Vacancy::getId)
                .findFirst()
                .orElse(0L);
    }

    @Mapping(target = "role", source = "roles", qualifiedByName = "roleToString")
    @Mapping(target = "resumeId", source = "resumes", qualifiedByName = "resumeToLong")
    @Mapping(target = "vacancyId", source = "vacancies", qualifiedByName = "vacancyToLong")
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}
