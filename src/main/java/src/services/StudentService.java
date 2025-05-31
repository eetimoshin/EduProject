package src.services;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import src.models.Student;
import src.repositories.StudentRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Optional<Student> findByLogin(String login) {
        return studentRepository.findByLogin(login);
    }

    public Optional<Student> findByStudentUuid(String uuid) {
        return studentRepository.findByStudentUuid(uuid);
    }

    public long getTime(String testId, HttpSession session) {
        Long startTime = (Long) session.getAttribute("testStartTime_" + testId);
        long endTime = System.currentTimeMillis();

        long durationMillis = 0;
        if (startTime != null) {
            durationMillis = endTime - startTime;
        }
        return durationMillis;
    }

    public String getStudentUuidFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        Student student = findByLogin(login).get();
        return student.getStudentUuid();
    }
}
