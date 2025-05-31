package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import src.models.Test;
import src.requests.TestRequest;
import src.services.TestService;

@Controller
@AllArgsConstructor
@RequestMapping("/tests")
public class TestController {

    private final TestService testService;

    @GetMapping
    public String showAllTests(Model model) {
        model.addAttribute("tests", testService.showAllTests());
        return "tests/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("test", new TestRequest("", ""));
        return "tests/create";
    }

    @PostMapping
    public String createTest(@ModelAttribute TestRequest testRequest) {
        testService.createTest(testRequest);
        return "redirect:/tests";
    }

    @GetMapping("/{testId}/edit")
    public String showEditForm(@PathVariable String testId, Model model) {
        Test test = testService.showTestById(testId);
        model.addAttribute("test", test);
        model.addAttribute("allTasks", testService.showAllTasks());
        return "tests/edit";
    }

    @PostMapping("/{testId}/update")
    public String updateTest(@PathVariable String testId, @ModelAttribute TestRequest testRequest) {
        testService.updateTest(testId, testRequest);
        return "redirect:/tests";
    }

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
