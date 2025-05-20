package src.controllers;

import src.services.TaskService;
import src.requests.TaskRequest;
import src.models.Test;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private final TaskService taskService;

    @Operation(summary = "Update task in test")
    @PutMapping("/task/{taskId}")
    public Test updateMessage(@PathVariable("taskId") String taskIdToUpdate,
                              @RequestBody TaskRequest updatedTaskRequest) {
        return taskService.update(taskIdToUpdate, updatedTaskRequest);
    }

    @Operation(summary = "Delete task")
    @DeleteMapping("/task/{taskId}")
    public void deleteMessage(@PathVariable("taskId") String taskId) {
        taskService.delete(taskId);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleException(ResponseStatusException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

//    @GetMapping
//    public Page<Message> getUsers(@RequestParam String role,
//                                  @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "10") int size) {
//        return messageService.getUsersByRole(role, page, size);
//    }
}
