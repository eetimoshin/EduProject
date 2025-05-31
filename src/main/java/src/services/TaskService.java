package src.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import src.models.Task;
import src.models.Test;
import src.repositories.TaskRepository;
import src.repositories.TestRepository;
import src.requests.TaskRequest;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TestRepository testRepository;

    public List<Task> showAllTasks() {
        return taskRepository.findAll();
    }

    public Task showTaskById(String id) {
        return taskRepository.findByTaskUuid(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message with ID: " + id + "  not found"));
    }

    public Task createTask(TaskRequest taskRequest) {
        validateMessageRequest(taskRequest);
        Task task = new Task();
        task.setTitle(taskRequest.title());
        task.setText(taskRequest.text());
        task.setCorrectAnswer(taskRequest.correctAnswer());
        task.setCreatedAt(OffsetDateTime.now());

        return taskRepository.save(task);
    }


    public Task updateTask(String messageIdToUpdate, TaskRequest updatedTaskRequest) {
        Task taskToUpdate = showTaskById(messageIdToUpdate);
        taskToUpdate.setTitle(updatedTaskRequest.title());
        taskToUpdate.setText(updatedTaskRequest.text());
        taskToUpdate.setCorrectAnswer(updatedTaskRequest.correctAnswer());

        return taskRepository.save(taskToUpdate);
    }

    public void deleteTask(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        List<Test> testsWithTask = testRepository.findAllByTasksContaining(task);
        for (Test test : testsWithTask) {
            test.getTasks().remove(task);
            testRepository.save(test);
        }

        taskRepository.delete(task);
    }

    private void validateMessageRequest(TaskRequest taskRequest) {
        if (taskRequest.text().isEmpty() || taskRequest.text().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Text of message is invalid");
        }
    }

    public boolean checkAnswer(String taskId, String userAnswer) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        return task.getCorrectAnswer().trim().equalsIgnoreCase(userAnswer.trim());
    }
}
