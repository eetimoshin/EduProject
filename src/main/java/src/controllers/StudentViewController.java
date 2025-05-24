package src.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import src.models.Course;
import src.models.Student;
import src.models.Test;
import src.models.Task;
import src.services.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentViewController {

    private final CourseService courseService;
    private final TestService testService;
    private final TaskService taskService;
    private final ResultService resultService;
    private final StudentService studentService;

    @GetMapping("/courses")
    public String viewCourses(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "student/courses";
    }

    // Показать тесты в выбранном курсе
    @GetMapping("/courses/{courseId}/tests")
    public String viewTests(@PathVariable String courseId, Model model) {
        List<Test> tests = courseService.getTestsByCourseId(courseId);
        model.addAttribute("tests", tests);
        model.addAttribute("courseId", courseId);
        return "student/tests";
    }


//    @GetMapping("/tests/{testId}/tasks")
//    public String viewCourse(@PathVariable("testId") String testId, Model model) {
//        Test test = testService.showTestById(testId);
//        model.addAttribute("test", test);
//        return "student/tasks";
//    }

    @GetMapping("/tests/{testId}/tasks")
    public String showPasswordForm(@PathVariable("testId") String testId, Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        Student student = studentService.findByLogin(login).get();
        String studentUuid = student.getStudentUuid();
        if (resultService.hasStudentPassedTest(testId, studentUuid)) {
            model.addAttribute("error", "Вы уже проходили этот тест и не можете пройти его снова.");
            return "student/test-password";  // создаёшь эту страницу с сообщением
        }

        // Проверим, не вводил ли студент уже пароль
        String sessionAttr = "access_granted_for_test_" + testId;

        if (Boolean.TRUE.equals(session.getAttribute(sessionAttr))) {
            // Если пароль уже введён — сразу показываем задачи
            Test test = testService.showTestById(testId);
            model.addAttribute("test", test);
            return "student/tasks";
        }
        // Иначе показываем форму для ввода пароля
        model.addAttribute("testId", testId);
        return "student/test-password";
    }

    @PostMapping("/tests/{testId}/tasks")
    public String checkTestPassword(@PathVariable("testId") String testId,
                                    @RequestParam String password,
                                    Model model,
                                    HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        Student student = studentService.findByLogin(login).get();
        String studentUuid = student.getStudentUuid();
        if (resultService.hasStudentPassedTest(testId, studentUuid)) {
            model.addAttribute("error", "Вы уже проходили этот тест и не можете пройти его снова.");
            return "student/test-password";  // создаёшь эту страницу с сообщением
        }

        Test test = testService.showTestById(testId);
        if (test.getPassword().equals(password)) {
            // Пароль верный — сохраняем флаг в сессии
            session.setAttribute("access_granted_for_test_" + testId, true);
            model.addAttribute("test", test);
            session.setAttribute("testStartTime_" + testId, System.currentTimeMillis());
            return "student/tasks";
        } else {
            // Пароль неверный — возвращаем форму с ошибкой
            model.addAttribute("testId", testId);
            model.addAttribute("error", "Неверный пароль. Попробуйте снова.");
            return "student/test-password";
        }
    }

    @PostMapping("/tests/{testId}/submit")
    public String submitTest(@PathVariable("testId") String testId,
                             @RequestParam MultiValueMap<String, String> formData,
                             Model model,
                             HttpSession session) {
        Long startTime = (Long) session.getAttribute("testStartTime_" + testId);
        long endTime = System.currentTimeMillis();

        long durationMillis = 0;
        if (startTime != null) {
            durationMillis = endTime - startTime;
        }
        model.addAttribute("durationSeconds", durationMillis / 1000);

        int percentage = testService.getResult(formData, testId);
        model.addAttribute("percentage", percentage);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        Student student = studentService.findByLogin(login).get();
        String studentUuid = student.getStudentUuid();
        resultService.saveAttempt(testId, studentUuid, percentage);
        return "student/test-result";
    }

}

