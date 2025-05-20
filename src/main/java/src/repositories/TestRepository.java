package src.repositories;

import src.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, String> {
    Optional<Test> findByTestUuid(String id);

}