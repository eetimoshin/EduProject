package src.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import src.models.EmptyTestResponse;
import src.models.Student;
import src.services.StudentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    @Operation(summary = "Show all students")
    @GetMapping("/students")
    public List<Student> showStudents() {
        return studentService.getAllStudents();
    }

    @Operation(summary = "Upload all students from CSV")
    @GetMapping("/students/upload/{filename}")
    public void uploadStudent(@PathVariable("filename") String filename) {
        studentService.saveStudentsFromCsv(filename);
    }

    @Operation(summary = "Delete students from CSV")
    @DeleteMapping("/students/delete/{filename}")
    public void deleteStudentsFromCsv(@PathVariable("filename") String filename) {
        studentService.deleteStudentsFromCsv(filename);
    }
}
