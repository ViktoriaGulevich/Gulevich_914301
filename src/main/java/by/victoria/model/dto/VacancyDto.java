package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VacancyDto {

    private Long id;

    @NotNull(message = "Заполните профессию")
    @NotEmpty(message = "Заполните профессию")
    private String profession;

    @NotNull(message = "Заполните должность")
    @NotEmpty(message = "Заполните должность")
    private String post;

    @NotNull(message = "Укажите опыт, если он не нужен, поставьте 0")
    @PositiveOrZero(message = "Опыт не должен быть отрицательным")
    private Integer workExperience;

    @NotNull(message = "Укажите минимальная зарплату")
    @Positive(message = "Зарплата должна быть больше нуля")
    private Integer salary;

    @NotNull(message = "Укажите необходимое количество рабочих часов")
    @Positive(message = "Время не должно быть отрицательным")
    @Min(value = 2, message = "Минимальное время 2 часа")
    @Max(value = 12, message = "Максимальное время 12 часов")
    private Integer timeOfEmployment;

    private String skills;
}
