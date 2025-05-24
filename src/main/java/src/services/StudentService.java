package src.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Value("${csv.base-path}")
    private String csvBasePath;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> findByLogin(String login) {
        return studentRepository.findByLogin(login);
    }

    public Optional<Student> findByStudentUuid(String uuid) {
        return studentRepository.findByStudentUuid(uuid);
    }

    public void saveStudentsFromCsv(String filename) {
        String fullPath = csvBasePath + filename + ".csv";
        Path path = Paths.get(fullPath);

        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + path.toAbsolutePath());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            List<Student> csvStudents = reader.lines()
                    .skip(1)
                    .map(this::mapToStudent)
                    .collect(Collectors.toList());

            for (Student csvStudent : csvStudents) {
                Optional<Student> existingOpt = studentRepository.findByLogin(csvStudent.getLogin());
                if (existingOpt.isPresent()) {
                    Student existing = existingOpt.get();
                    existing.setName(csvStudent.getName());
                    existing.setSurname(csvStudent.getSurname());
                    existing.setAcademicGroup(csvStudent.getAcademicGroup());
                    existing.setCourse(csvStudent.getCourse());
                    existing.setPassword(csvStudent.getPassword());
                    existing.setComment(csvStudent.getComment());
                    studentRepository.save(existing);
                } else {
                    studentRepository.save(csvStudent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Student mapToStudent(String line) {
        String[] fields = line.split(",", -1); // -1 чтобы сохранить пустые поля
        Student student = new Student();

        student.setName(fields.length > 0 ? fields[0].trim() : "");
        student.setSurname(fields.length > 1 ? fields[1].trim() : "");
        student.setAcademicGroup(fields.length > 2 ? fields[2].trim() : "");
        student.setCourse(fields.length > 3 ? fields[3].trim() : "");
        student.setLogin(fields.length > 4 ? fields[4].trim() : "");
        student.setPassword(fields.length > 5 ? fields[5].trim() : "");
        student.setComment(fields.length > 6 ? fields[6].trim() : "");

        return student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudentsFromCsv(String filename) {
        Path path = Paths.get(csvBasePath, filename + ".csv");

        if (!Files.exists(path)) {
            System.out.println("Файл не найден: " + path.toAbsolutePath());
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",", -1); // -1 — сохранить пустые поля
                if (fields.length >= 5) {
                    String login = fields[4].trim();
                    Optional<Student> studentOpt = studentRepository.findByLogin(login);
                    studentOpt.ifPresent(studentRepository::delete);
                }
            }
        } catch (IOException e) {
        }
    }

}
