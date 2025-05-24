package src.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.models.Result;
import src.models.Student;

public interface ResultRepository extends JpaRepository<Result, String> {

    boolean existsByTestUuidAndStudentUuid(String testUuid, String studentUuid);

}
