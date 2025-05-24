package src.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tasks")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String testUuid;
    private String title;
    private OffsetDateTime createdAt;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "test_tasks", // имя таблицы-связки
            joinColumns = @JoinColumn(name = "test_uuid"),
            inverseJoinColumns = @JoinColumn(name = "task_uuid")
    )
    private List<Task> tasks = new ArrayList<>();

    @ManyToMany(mappedBy = "tests") // обратная сторона связи с Course
    private List<Course> courses = new ArrayList<>();
}
