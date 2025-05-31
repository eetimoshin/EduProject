package src.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import src.models.Course;
import src.models.Task;
import src.models.Test;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {

    Optional<Course> findByCourseUuid(String id);

}
