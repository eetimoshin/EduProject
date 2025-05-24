package src.services;

import org.springframework.util.MultiValueMap;
import src.models.Task;
import src.models.Test;
import src.repositories.TaskRepository;
import src.repositories.TestRepository;
import src.requests.TestRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;
    private final TaskRepository taskRepository;


    public Test addTaskToTest(String testId, String taskId) {
        Test test = testRepository.findById(testId).orElse(null);
        if (test == null) {
            return null;
        }

        Task task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return null;
        }

        if (!test.getTasks().contains(task)) {
            test.getTasks().add(task);
        }
        return testRepository.save(test);
    }



    public List<Test> showAllTests() {
        return testRepository.findAll();
    }

    public Test showTestById(String id) {
        return testRepository.findByTestUuid(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Test with ID: " + id + "  not found"));
    }

    public Test createTest(TestRequest testRequest) {
        validateMessageRequest(testRequest);
        Test test = new Test();
        test.setTitle(testRequest.title());
        test.setPassword(testRequest.password());
        test.setCreatedAt(OffsetDateTime.now());
        return testRepository.save(test);
    }


    public Test updateTest(String messageIdToUpdate, TestRequest updatedTestRequest) {
        Test testToUpdate = showTestById(messageIdToUpdate);
        testToUpdate.setTitle(updatedTestRequest.title());
        testToUpdate.setPassword(updatedTestRequest.password());
        return testRepository.save(testToUpdate);
    }

    public void deleteTest(String id) {
        Test test = showTestById(id);
        testRepository.delete(test);
    }

    private void validateMessageRequest(TestRequest testRequest) {
        if (testRequest.title().isEmpty() || testRequest.title().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Title of test is invalid");
        }
        checkTestDoesNotExist(testRequest.title());
    }

    private void checkTestDoesNotExist(String text) {
        if (testRepository.findByTitle(text).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Test with such name already exists");
        }
    }

    public List<Task> showAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> showAllTasks(String testId) {
        return taskRepository.findAllByTests_TestUuid(testId);
    }

    public List<Task> showTasksByTestId(String testId) {
        return taskRepository.findAllByTestId(testId);
    }


    public Integer getResult(MultiValueMap<String, String> formData, String testId) {

        Test test = showTestById(testId);
        int total = test.getTasks().size();
        int correct = 0;

        for (Task task : test.getTasks()) {
            String answerKey = task.getTaskUuid();
            String studentAnswer = formData.getFirst(answerKey);
            if (studentAnswer != null && studentAnswer.trim().equalsIgnoreCase(task.getCorrectAnswer().trim())) {
                correct++;
            }
        }

        int percentage = (int) ((double) correct / total * 100);
        return percentage;
    }

}

