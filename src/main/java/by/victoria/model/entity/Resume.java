package by.victoria.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fio;

    @Column
    private String sex;

    @Column
    private LocalDate dateOfBirth;

    @Column
    private String profession;

    @Column
    private String post;

    @Column
    private Integer workExperience;

    @Column
    private String lastWork;

    @Column
    private String typeOfEmployment;

    @Column
    private Integer salary;

    @Column
    private Integer timeOfEmployment;

    @Transient
    @ManyToMany(mappedBy = "resumes")
    private Set<User> users;

    @Transient
    @ManyToMany(mappedBy = "favorites")
    private Set<User> recruiters;

    @Transient
    @ManyToMany(mappedBy = "toSends")
    private Set<User> recruitersToSend;

    @ManyToMany
    @JoinTable(
            name = "resume_skill_links",
            joinColumns = {@JoinColumn(name = "resume_id")},
            inverseJoinColumns = {@JoinColumn(name = "key_skill_id")}
    )
    private Set<KeySkill> skills;

    public Integer getAge() {
        long now = LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        long birthday = dateOfBirth.atStartOfDay().toEpochSecond(ZoneOffset.UTC);
        return (int) ((now - birthday) / 3600 / 24 / 365);
    }
}
