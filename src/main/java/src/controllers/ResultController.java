package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import src.models.Result;
import src.models.Student;
import src.models.Test;
import src.services.ResultService;
import src.services.StudentService;
import src.services.TestService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final StudentService studentService;
    private final TestService testService;

    @GetMapping("/results")
    public String showResultsLog(Model model) {
        List<Result> results = resultService.findAllResults();

        List<ResultDTO> resultDTOs = results.stream().map(r -> {
            Student student = studentService.findByStudentUuid(r.getStudentUuid()).orElse(null);
            Test test = testService.showTestById(r.getTestUuid());
            String studentLogin = (student != null) ? student.getLogin() : "Не найден";
            String testTitle = (test != null) ? test.getTitle() : "Не найден";

            return new ResultDTO(
                    studentLogin,
                    testTitle,
                    r.getAttemptTime(),
                    r.getPercentage()
            );
        }).collect(Collectors.toList());

        model.addAttribute("results", resultDTOs);
        return "professor/results-log";
    }

    public static record ResultDTO(String studentLogin, String testTitle,
                                   java.time.OffsetDateTime attemptTime, Integer percentage) {}
}
