package by.victoria.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class KeySkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Transient
    @ManyToMany(mappedBy = "skills")
    private Set<Resume> resumes;

    @Transient
    @ManyToMany(mappedBy = "skills")
    private Set<Vacancy> vacancies;

    public KeySkill(String name) {
        this.name = name;
    }
}
