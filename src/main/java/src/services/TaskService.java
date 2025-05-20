package src.services;

import src.models.Task;
import src.repositories.TaskRepository;
import src.requests.TaskRequest;
import src.models.Test;
import src.repositories.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TestRepository testRepository;

    public Task save(TaskRequest taskRequest, String topicId) {
        validateMessageRequest(taskRequest);
        Task task = new Task();
        task.setText(taskRequest.text());
        task.setCreatedAt(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString());
        task.setTest(testRepository.findByTestUuid(topicId).orElse(null));
        return taskRepository.save(task);
    }


    public Test update(String messageIdToUpdate, TaskRequest updatedTaskRequest) {
        Task taskToUpdate = findByUuid(messageIdToUpdate);
        validateMessageRequest(updatedTaskRequest);

        taskToUpdate.setText(updatedTaskRequest.text());
        taskRepository.save(taskToUpdate);

        return testRepository.findByTestUuid(taskToUpdate.getTest().getTestUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic not found"));
    }

    public void delete(String id) {
        Task task = findByUuid(id);
        taskRepository.delete(task);
    }

    private void validateMessageRequest(TaskRequest taskRequest) {
        if (taskRequest.text().isEmpty() || taskRequest.text().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Text of message is invalid");
        }
    }

    private Task findByUuid(String id) {
        return taskRepository.findByTaskUuid(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found with ID: " + id));
    }
}
