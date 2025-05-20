package src.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EmptyTestResponse {
    private String testId;
    private String testName;
    private String createdAt;
}
