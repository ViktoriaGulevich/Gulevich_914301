package by.victoria.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@Table
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String profession;

    @Column
    private String post;

    @Column
    private Integer workExperience;

    @Column
    private Integer salary;

    @Column
    private Integer timeOfEmployment;

    @Transient
    @ManyToMany(mappedBy = "vacancies")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "user_respond_links",
            joinColumns = {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> respondUsers;

    @ManyToMany
    @JoinTable(
            name = "vacancy_skill_links",
            joinColumns = {@JoinColumn(name = "vacancy_id")},
            inverseJoinColumns = {@JoinColumn(name = "key_skill_id")}
    )
    private Set<KeySkill> skills;


    public void addRespondVacancy(User user) {
        respondUsers = Objects.requireNonNullElseGet(respondUsers, HashSet::new);

        respondUsers.add(user);
    }

    public Vacancy(){
        respondUsers=new HashSet<>();
    }
}
