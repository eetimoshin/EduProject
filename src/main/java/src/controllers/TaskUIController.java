package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.ResponseEntity;
import src.models.Task;
import src.requests.CheckRequest;
import src.requests.TaskRequest;
import src.services.TaskService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskUIController {

    private final TaskService taskService;

    @GetMapping
    public String showTasks(Model model) {
        List<Task> tasks = taskService.showAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/{taskId}")
    public String showTask(@PathVariable String taskId, Model model) {
        Task task = taskService.showTaskById(taskId);
        model.addAttribute("task", task);
        return "tasks/view";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("taskRequest", new TaskRequest("", "", ""));
        return "tasks/create";
    }

    @PostMapping
    public String createTask(@ModelAttribute TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
        return "redirect:/tasks";
    }

    @GetMapping("/{taskId}/edit")
    public String showEditForm(@PathVariable String taskId, Model model) {
        Task task = taskService.showTaskById(taskId);
        model.addAttribute("task", task);
        return "tasks/edit";
    }

    @PostMapping("/{taskId}/update")
    public String updateTask(@PathVariable String taskId, @ModelAttribute TaskRequest taskRequest) {
        taskService.updateTask(taskId, taskRequest);
        return "redirect:/tasks";
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable String taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/tasks";
    }

    @PostMapping("/{taskId}/check")
    @ResponseBody
    public ResponseEntity<Boolean> checkAnswer(@PathVariable String taskId, @RequestBody CheckRequest checkRequest) {
        boolean isCorrect = taskService.checkAnswer(taskId, checkRequest.userAnswer());
        return ResponseEntity.ok(isCorrect);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(ResponseStatusException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
}
