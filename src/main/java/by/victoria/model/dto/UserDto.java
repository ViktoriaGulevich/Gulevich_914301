package by.victoria.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private Long id;

    @NotNull(message = "Введите логин")
    @NotEmpty(message = "Введите логин")
    private String login;

    @NotNull(message = "Введите пароль")
    @NotEmpty(message = "Введите пароль")
    @Size(min = 6, max = 20, message = "Пароль должен иметь длинну от 6 до 20 символов")
    private String password;

    @NotNull(message = "Введите электронную почту")
    @NotEmpty(message = "Введите электронную почту")
    @Email(message = "Введите корректную почту")
    private String email;

    private String role;

    private Long resumeId;

    private Long vacancyId;

    @Getter(AccessLevel.NONE)
    private Boolean isRecruiter;

    public Boolean isRecruiter() {
        return isRecruiter != null && isRecruiter;
    }
}
