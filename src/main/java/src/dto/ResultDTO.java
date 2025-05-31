package src.dto;

import java.time.OffsetDateTime;

public record ResultDTO(String studentLogin, String testTitle,
                        OffsetDateTime attemptTime, Integer percentage) {
}
