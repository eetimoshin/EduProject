package src.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String resultUuid;
    private OffsetDateTime attemptTime;
    private Integer percentage;

    @ManyToOne
    @JoinColumn(name = "student_uuid")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_uuid")
    private Test test;
}