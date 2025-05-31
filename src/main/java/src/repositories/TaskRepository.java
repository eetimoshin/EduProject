package src.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import src.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import src.models.Test;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<Task> findByTaskUuid(String id);

    List<Task> findAllByTests_TestUuid(String testUuid);

    @Query("SELECT t FROM Task t JOIN t.tests test WHERE test.testUuid = :testId")
    List<Task> findAllByTestId(@Param("testId") String testId);

}