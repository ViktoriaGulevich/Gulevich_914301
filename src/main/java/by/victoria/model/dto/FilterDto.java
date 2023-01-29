package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterDto {
    private Boolean sexM;
    private Boolean sexW;
    private Integer ageFrom;
    private Integer ageTo;
    private String post;
    private String profession;
    private Integer workExperience;
    private String typeOfEmployment;
    private Integer salary;
    private Integer timeOfEmployment;
    private String skills;
    private Boolean isRespond;
}
