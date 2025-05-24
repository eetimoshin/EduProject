package src.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import src.models.Student;
import src.services.StudentService;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/students")
public class StudentUIController {

    private final StudentService studentService;

    @GetMapping
    public String showStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students/list";
    }

    @PostMapping("/upload")
    public String uploadStudents(@RequestParam("filename") String filename) {
        studentService.saveStudentsFromCsv(filename);
        return "redirect:/students";
    }

    @PostMapping("/delete")
    public String deleteStudentsFromCsv(@RequestParam("filename") String filename) {
        studentService.deleteStudentsFromCsv(filename);
        return "redirect:/students";
    }
}
