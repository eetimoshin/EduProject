package src.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.models.Result;

public interface ResultRepository extends JpaRepository<Result, String> {

    boolean existsByTest_TestUuidAndStudent_StudentUuid(String testUuid, String studentUuid);

}
