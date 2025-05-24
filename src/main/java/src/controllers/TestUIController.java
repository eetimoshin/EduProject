package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import src.models.Task;
import src.models.Test;
import src.requests.TestRequest;
import src.services.TestService;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tests")
public class TestUIController {

    private final TestService testService;

    // Показать все тесты
    @GetMapping
    public String showAllTests(Model model) {
        model.addAttribute("tests", testService.showAllTests());
        return "tests/list"; // /resources/templates/tests/list.html
    }

    // Форма создания нового теста
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("test", new TestRequest("", ""));
        return "tests/create";
    }

    // Обработка создания
    @PostMapping
    public String createTest(@ModelAttribute TestRequest testRequest) {
        testService.createTest(testRequest);
        return "redirect:/tests";
    }

    // Показать форму редактирования теста
    @GetMapping("/{testId}/edit")
    public String showEditForm(@PathVariable String testId, Model model) {
        Test test = testService.showTestById(testId);
        model.addAttribute("test", test);

        // Добавить все доступные задачи в модель
        model.addAttribute("allTasks", testService.showAllTasks());

        return "tests/edit";
    }

    // Обновление теста
    @PostMapping("/{testId}/update")
    public String updateTest(@PathVariable String testId, @ModelAttribute TestRequest testRequest) {
        testService.updateTest(testId, testRequest);
        return "redirect:/tests";
    }

    // Удаление теста
    @PostMapping("/{testId}/delete")
    public String deleteTest(@PathVariable String testId) {
        testService.deleteTest(testId);
        return "redirect:/tests";
    }

    @PostMapping("/{testId}/add-task")
    public String addTaskToTest(@PathVariable String testId, @RequestParam String taskId) {
        testService.addTaskToTest(testId, taskId);
        return "redirect:/tests/" + testId + "/edit";
    }

    @GetMapping("/{id}/view")
    public String viewTest(@PathVariable("id") String testId, Model model) {
        Test test = testService.showTestById(testId);
        model.addAttribute("test", test);
        return "tests/view";
    }
}
