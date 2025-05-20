package src.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskUuid;
    private String text;
    private String createdAt;

    @ManyToOne
    @JoinColumn(name = "testUuid")
    @JsonBackReference
    private Test test;
}
