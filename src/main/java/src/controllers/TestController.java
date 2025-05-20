package src.controllers;

import io.swagger.v3.oas.annotations.Operation;
import src.services.TaskService;
import src.requests.TaskRequest;
import src.requests.EmptyTestRequest;
import src.requests.TestWithMessageRequest;
import src.models.Test;
import src.models.EmptyTestResponse;
import src.services.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TestController {

    private final TestService testService;
    private final TaskService taskService;

    @Operation(summary = "Get list of all tests")
    @GetMapping("/test")
    public List<EmptyTestResponse> listAllTopics() {
        return testService.show();
    }

    @Operation(summary = "Create test")
    @PostMapping("/test")
    public Test createTest(@RequestBody TestWithMessageRequest testWithMessageRequest) {
        EmptyTestRequest emptyTestRequest = testWithMessageRequest.getEmptyTestRequest();
        TaskRequest taskRequest = testWithMessageRequest.getTaskRequest();
        Test test = testService.save(emptyTestRequest, taskRequest);
        taskService.save(taskRequest, test.getTestUuid());
        return test;
    }

    @Operation(summary = "Add task to test")
    @PostMapping("/test/{testId}/task")
    public Test createTask(@PathVariable("testId") String testId,
                              @RequestBody TaskRequest taskRequest) {
        taskService.save(taskRequest, testId);
        return testService.findByUuid(testId);
    }

    @Operation(summary = "Get test and all tasks in it")
    @GetMapping("/test/{testId}")
    public Test getTest(@PathVariable("testId") String testId) {
        return testService.findByUuid(testId);
    }

    @Operation(summary = "Update test")
    @PutMapping("/test/{topicId}")
    public Test updateTest(@PathVariable("topicId") String testId,
                            @RequestBody EmptyTestRequest emptyTestRequest) {
        return testService.update(testId, emptyTestRequest);
    }

    @Operation(summary = "Delete test")
    @DeleteMapping("/test/{testId}")
    public void deleteTest(@PathVariable("testId") String testId) {
        testService.delete(testId);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(ResponseStatusException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
}