package src.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import src.models.Student;
import src.models.Task;
import src.repositories.StudentRepository;
import src.repositories.TaskRepository;
import src.requests.TaskRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public void saveStudentsFromCsv(String filename) {
        filename = filename + ".csv";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (is == null) {
                throw new RuntimeException("File not found: " + filename);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            List<Student> students = reader.lines()
                    .skip(1) // пропускаем заголовок, если есть
                    .map(this::mapToStudent)
                    .collect(Collectors.toList());

            studentRepository.saveAll(students);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }

    private Student mapToStudent(String line) {
        String[] fields = line.split(","); // если разделитель — запятая
        Student student = new Student();
        student.setFirstName(fields[0].trim());
        student.setSurname(fields[1].trim());
        student.setLastName(fields[2].trim());
        student.setCourse(fields[3].trim());
        student.setLogin(fields[4].trim());
        student.setPassword(fields[5].trim());
        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudentsFromCsv(String filename) {
        filename = filename + ".csv";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (is == null) {
                throw new RuntimeException("File not found: " + filename);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String login = fields[4].trim();

                Optional<Student> studentOpt = studentRepository.findByLogin(login);
                studentOpt.ifPresent(studentRepository::delete);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file " + filename, e);
        }
    }
}
