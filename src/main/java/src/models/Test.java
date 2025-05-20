package src.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@ToString
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String testUuid;
    private String testName;
    private String createdAt;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    @OrderBy("createdAt ASC")
    private List<Task> tasks;

    public void addToMessages(Task task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }
}
