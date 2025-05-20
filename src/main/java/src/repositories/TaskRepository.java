package src.repositories;

import src.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {
    Optional<Task> findByTaskUuid(String id);

//    Page<Message> findByRole(String role, Pageable pageable);
}




