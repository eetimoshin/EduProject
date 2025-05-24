package src.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import src.models.Result;
import src.models.Task;
import src.models.Test;
import src.repositories.CourseRepository;
import src.repositories.ResultRepository;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    public void saveAttempt(String testUuid, String studentUuid, Integer percentage) {
        Result result = new Result();
        result.setTestUuid(testUuid);
        result.setStudentUuid(studentUuid);
        result.setPercentage(percentage);
        result.setAttemptTime(OffsetDateTime.now());
        resultRepository.save(result);
    }

    public boolean hasStudentPassedTest(String testUuid, String studentUuid) {
        return resultRepository.existsByTestUuidAndStudentUuid(testUuid, studentUuid);
    }

    public List<Result> findAllResults() {
        return resultRepository.findAll();
    }
}
