package src.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String courseUuid;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "course_test",
            joinColumns = @JoinColumn(name = "course_uuid"),
            inverseJoinColumns = @JoinColumn(name = "test_uuid")
    )
    private Set<Test> tests = new HashSet<>();
}

