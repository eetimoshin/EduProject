package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import src.models.Result;
import src.dto.ResultDTO;
import src.services.ResultService;

import java.util.List;

@Controller
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/results")
    public String showResultsLog(Model model) {
        List<Result> results = resultService.findAllResults();
        List<ResultDTO> resultDTOs = resultService.showResults(results);
        model.addAttribute("results", resultDTOs);
        return "professor/results-log";
    }
}
