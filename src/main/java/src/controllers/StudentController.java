package src.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import src.models.Course;
import src.models.Test;
import src.services.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {

    private final CourseService courseService;
    private final TestService testService;
    private final ResultService resultService;
    private final StudentService studentService;

    @GetMapping("/courses")
    public String viewCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "student/courses";
    }

    @GetMapping("/courses/{courseId}/tests")
    public String viewTests(@PathVariable String courseId, Model model) {
        List<Test> tests = courseService.getTestsByCourseId(courseId);
        model.addAttribute("tests", tests);
        model.addAttribute("courseId", courseId);
        return "student/tests";
    }


    @GetMapping("/tests/{testId}/tasks")
    public String showPasswordForm(@PathVariable("testId") String testId, Model model, HttpSession session) {
        String studentUuid = studentService.getStudentUuidFromAuth();
        if (resultService.hasStudentPassedTest(testId, studentUuid)) {
            model.addAttribute("error", "Вы уже проходили этот тест и не можете пройти его снова.");
            return "student/testPasswordForm";
        }

        String sessionAttr = "access_granted_for_test_" + testId;
        if (Boolean.TRUE.equals(session.getAttribute(sessionAttr))) {
            Test test = testService.showTestById(testId);
            model.addAttribute("test", test);
            return "student/tasks";
        }
        model.addAttribute("testId", testId);
        return "student/testPasswordForm";
    }

    @PostMapping("/tests/{testId}/tasks")
    public String checkTestPassword(@PathVariable("testId") String testId,
                                    @RequestParam String password,
                                    Model model,
                                    HttpSession session) {
        String studentUuid = studentService.getStudentUuidFromAuth();
        if (resultService.hasStudentPassedTest(testId, studentUuid)) {
            model.addAttribute("error", "Вы уже проходили этот тест и не можете пройти его снова.");
            return "student/testPasswordForm";
        }

        Test test = testService.showTestById(testId);
        if (test.getPassword().equals(password)) {
            session.setAttribute("access_granted_for_test_" + testId, true);
            model.addAttribute("test", test);
            session.setAttribute("testStartTime_" + testId, System.currentTimeMillis());
            return "student/tasks";
        } else {
            model.addAttribute("testId", testId);
            model.addAttribute("error", "Неверный пароль. Попробуйте снова.");
            return "student/testPasswordForm";
        }
    }

    @PostMapping("/tests/{testId}/submit")
    public String submitTest(@PathVariable("testId") String testId,
                             @RequestParam MultiValueMap<String, String> formData,
                             Model model,
                             HttpSession session) {
        long durationMillis = studentService.getTime(testId, session);
        model.addAttribute("durationSeconds", durationMillis / 1000);

        int percentage = testService.getResult(formData, testId);
        model.addAttribute("percentage", percentage);

        String studentUuid = studentService.getStudentUuidFromAuth();
        resultService.saveAttempt(testId, studentUuid, percentage);
        return "student/testResultForm";
    }
}