package src.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.models.Professor;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, String> {
    Optional<Professor> findByLogin(String login);
}

