package src.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestWithMessageRequest {
    private EmptyTestRequest emptyTestRequest;
    private TaskRequest taskRequest;
}
