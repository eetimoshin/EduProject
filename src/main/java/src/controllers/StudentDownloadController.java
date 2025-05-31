package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import src.models.Student;
import src.services.StudentDownloadService;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentDownloadController {

    private final StudentDownloadService studentDownloadService;

    @GetMapping
    public String showStudents(Model model) {
        List<Student> students = studentDownloadService.getAllStudents();
        model.addAttribute("students", students);
        return "studentDownload/list";
    }

    @PostMapping("/upload")
    public String uploadStudents(@RequestParam("filename") String filename) {
        studentDownloadService.saveStudentsFromCsv(filename);
        return "redirect:/students";
    }

    @PostMapping("/delete")
    public String deleteStudentsFromCsv(@RequestParam("filename") String filename) {
        studentDownloadService.deleteStudentsFromCsv(filename);
        return "redirect:/students";
    }
}
