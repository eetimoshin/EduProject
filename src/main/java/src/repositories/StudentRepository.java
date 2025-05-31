package src.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.models.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {

    Optional<Student> findByLogin(String login);

    Optional<Student> findByStudentUuid(String uuid);

}
