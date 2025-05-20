package src.services;

import src.models.Task;
import src.requests.TaskRequest;
import src.requests.EmptyTestRequest;
import src.models.Test;
import src.models.EmptyTestResponse;
import src.repositories.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    public Test save(EmptyTestRequest emptyTestRequest, TaskRequest taskRequest) {
        validateTopicRequest(emptyTestRequest);
        if (taskRequest.text().isEmpty() || taskRequest.text().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Text of message is invalid");
        }
        Test test = new Test();

        test.setTestName(emptyTestRequest.topicName());
        test.setCreatedAt(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        Task task = new Task();
        task.setText(taskRequest.text());
        task.setCreatedAt(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        test.addToMessages(task);
        return testRepository.save(test);
    }

    public List<EmptyTestResponse> show() {
        List<Test> tests = testRepository.findAll();
        List<EmptyTestResponse> newTopics = new ArrayList<>();
        for (Test test : tests) {
            EmptyTestResponse newTopic = EmptyTestResponse.builder()
                    .testId(test.getTestUuid())
                    .testName(test.getTestName())
                    .createdAt(test.getCreatedAt())
                    .build();
            newTopics.add(newTopic);
        }
        return newTopics;
    }

    public Test findByUuid(String id) {
        return testRepository.findByTestUuid(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
    }

    public Test update(String id, EmptyTestRequest emptyTestRequest) {
        Test newTest = findByUuid(id);

        validateTopicRequest(emptyTestRequest);
        Test test = new Test();
        test.setTestName(emptyTestRequest.topicName());
        newTest.setTestName(test.getTestName());
        return testRepository.save(newTest);
    }

    public void delete(String id) {
        testRepository.deleteById(id);

    }

    private void validateTopicRequest(EmptyTestRequest emptyTestRequest) {
        if (emptyTestRequest.topicName().isEmpty() || emptyTestRequest.topicName().length() > 20){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic invalid");
        }
    }
}
