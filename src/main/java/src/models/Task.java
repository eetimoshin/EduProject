package src.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "tests")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskUuid;
    private String title;
    private String text;

    @JsonIgnore
    private String correctAnswer;

    private OffsetDateTime createdAt;

    @ManyToMany(mappedBy = "tasks")
    @JsonBackReference
    private List<Test> tests = new ArrayList<>();
}
