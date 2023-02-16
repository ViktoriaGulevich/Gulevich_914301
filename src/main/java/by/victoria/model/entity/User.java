package by.victoria.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String login;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "user_resume_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "resume_id")}
    )
    private Set<Resume> resumes;

    @ManyToMany
    @JoinTable(
            name = "recruiter_resume_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "resume_id")}
    )
    private Set<Resume> favorites;

    @ManyToMany
    @JoinTable(
            name = "recruiter_resume_to_send_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "resume_id")}
    )
    private Set<Resume> toSends;

    @ManyToMany
    @JoinTable(
            name = "user_vacancy_links",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "vacancy_id")}
    )
    private Set<Vacancy> vacancies;

    @Transient
    @ManyToMany(mappedBy = "respondUsers")
    private Set<Vacancy> responds;

    public void addRole(Role role) {
        roles = Objects.requireNonNullElseGet(roles, HashSet::new);

        roles.add(role);
    }

    public void addResumeToSend(Resume resume) {
        toSends = Objects.requireNonNullElseGet(toSends, HashSet::new);

        deleteFromFavorites(resume);

        toSends.add(resume);
    }

    public void addResumeToFavorites(Resume resume) {
        favorites = Objects.requireNonNullElseGet(favorites, HashSet::new);

        favorites.add(resume);
    }

    public void deleteFromFavorites(Resume resume) {
        if (favorites==null){
            return;
        }
        if (!favorites.contains(resume)) {
            return;
        }
        favorites.remove(resume);
    }

    public void deleteFromSend(Resume resume) {
        if (toSends==null){
            return;
        }
        if (!toSends.contains(resume)) {
            return;
        }
        toSends.remove(resume);
        addResumeToFavorites(resume);
    }

    public void addResume(Resume resume) {
        resumes = Objects.requireNonNullElseGet(resumes, HashSet::new);

        resumes.add(resume);
    }

    public void addVacancy(Vacancy vacancy) {
        vacancies = Objects.requireNonNullElseGet(vacancies, HashSet::new);

        vacancies.add(vacancy);
    }
}
