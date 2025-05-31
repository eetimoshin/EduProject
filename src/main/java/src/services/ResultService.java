package src.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import src.models.Result;
import src.dto.ResultDTO;
import src.models.Student;
import src.models.Test;
import src.repositories.ResultRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final StudentService studentService;
    private final TestService testService;

    public void saveAttempt(String testUuid, String studentUuid, Integer percentage) {
        Student student = studentService.findByStudentUuid(studentUuid)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentUuid));

        Test test = testService.findByTestUuid(testUuid)
                .orElseThrow(() -> new IllegalArgumentException("Test not found: " + testUuid));

        Result result = new Result();
        result.setTest(test);
        result.setStudent(student);
        result.setPercentage(percentage);
        result.setAttemptTime(OffsetDateTime.now());

        resultRepository.save(result);
    }

    public boolean hasStudentPassedTest(String testUuid, String studentUuid) {
        return resultRepository.existsByTest_TestUuidAndStudent_StudentUuid(testUuid, studentUuid);
    }

    public List<Result> findAllResults() {
        return resultRepository.findAll();
    }

    public List<ResultDTO> showResults(List<Result> results) {
        return results.stream().map(r -> {
            Student student = r.getStudent();
            Test test = r.getTest();

            String studentLogin = (student != null) ? student.getLogin() : "Не найден";
            String testTitle = (test != null) ? test.getTitle() : "Не найден";

            return new ResultDTO(
                    studentLogin,
                    testTitle,
                    r.getAttemptTime(),
                    r.getPercentage()
            );
        }).collect(Collectors.toList());
    }
}
