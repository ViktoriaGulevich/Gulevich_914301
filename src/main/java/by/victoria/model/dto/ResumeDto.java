package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResumeDto {

    private Long id;

    @NotNull(message = "Заполните фамилию")
    @NotEmpty(message = "Заполните фамилию")
    private String fio;

    @NotNull(message = "Укажите пол")
    @NotEmpty(message = "Укажите пол")
    @Pattern(regexp = "[МЖ]", message = "Пол должен быть введен русскими буквами 'М' или 'Ж'")
    private String sex;

    @NotNull(message = "Введите дату")
    @Past(message = "Введите корректную дату, используя паттерн ГГГГ-ММ-ДД")
    private LocalDate dateOfBirth;

    @NotNull(message = "Заполните профессию")
    @NotEmpty(message = "Заполните профессию")
    private String profession;

    @NotNull(message = "Заполните должность")
    @NotEmpty(message = "Заполните должность")
    private String post;

    @NotNull(message = "Укажите опыт, если его нет, поставьте 0")
    @PositiveOrZero(message = "Опыт не должен быть отрицательным")
    private Integer workExperience;

    private String lastWork;

    @NotNull(message = "Заполните тип занятости")
    @NotEmpty(message = "Заполните тип занятости")
    @Pattern(regexp = "очное|удаленно", message = "Тип занятости должен быть 'очное' или 'удаленно'")
    private String typeOfEmployment;

    @NotNull(message = "Укажите зарплату")
    @Positive(message = "Зарплата должна быть больше нуля")
    private Integer salary;

    @NotNull(message = "Укажите желаемое количество рабочих часов")
    @Positive(message = "Время не должно быть отрицательным")
    @Min(value = 2, message = "Минимальное время 2 часа")
    @Max(value = 12, message = "Максимальное время 12 часов")
    private Integer timeOfEmployment;

    private String skills;
}
