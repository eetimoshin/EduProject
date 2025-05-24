package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import src.models.Result;
import src.services.ResultService;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProfessorUIController {

    private final ResultService resultService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Result> results = resultService.findAllResults();
        model.addAttribute("results", results);
        return "professor/dashboard";
    }

}
