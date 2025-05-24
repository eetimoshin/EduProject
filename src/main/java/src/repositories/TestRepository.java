package src.repositories;

import src.models.Task;
import src.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, String> {

    Optional<Test> findByTestUuid(String id);

    Optional<Test> findByTitle(String title);

    List<Test> findAllByTasksContaining(Task task);
}