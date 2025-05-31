package src.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import src.models.Course;
import src.models.Test;
import src.services.CourseService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public String listCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "course/list";
    }

    @GetMapping("/new")
    public String createCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/create";
    }

    @PostMapping
    public String saveCourse(@ModelAttribute Course course) {
        courseService.saveCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/{id}")
    public String viewCourse(@PathVariable String id, Model model) {
        Optional<Course> courseOpt = courseService.findById(id);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            List<Test> availableTests = courseService.getAvailableTestsForCourse(course);
            model.addAttribute("course", course);
            model.addAttribute("availableTests", availableTests);
            return "course/view";
        } else {
            return "redirect:/courses";
        }
    }

    @PostMapping("/{id}/add-test")
    public String addTestToCourse(@PathVariable String id, @RequestParam String testId) {
        courseService.addTestToCourse(id, testId);
        return "redirect:/courses/" + id;
    }

    @GetMapping("/{id}/edit")
    public String editCourseForm(@PathVariable String id, Model model) {
        Optional<Course> courseOpt = courseService.findById(id);
        if (courseOpt.isPresent()) {
            model.addAttribute("course", courseOpt.get());
            return "course/edit";
        }
        return "redirect:/courses";
    }

    @PostMapping("/{id}/edit")
    public String updateCourse(@PathVariable String id, @ModelAttribute Course updatedCourse) {
        courseService.updateCourse(id, updatedCourse);
        return "redirect:/courses/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }
}
