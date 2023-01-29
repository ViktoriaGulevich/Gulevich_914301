package by.victoria.repository;

import by.victoria.model.entity.KeySkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeySkillRepository extends JpaRepository<KeySkill, Long> {
    Optional<KeySkill> findByName(String name);
}
