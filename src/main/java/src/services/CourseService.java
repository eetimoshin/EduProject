package src.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import src.models.Course;
import src.models.Test;
import src.repositories.CourseRepository;
import src.repositories.TestRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final TestRepository testRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(String id) {
        return courseRepository.findById(id);
    }

    public void saveCourse(Course course) {
//        course.setProfessor();
        courseRepository.save(course);
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public void updateCourse(String id, Course updatedCourse) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setName(updatedCourse.getName());
            // если нужны другие поля — тоже обнови их здесь
            courseRepository.save(course);
        }
    }

    public List<Test> getAvailableTestsForCourse(Course course) {
        List<Test> allTests = testRepository.findAll();
        allTests.removeAll(course.getTests());
        return allTests;
    }

    public Course addTestToCourse(String courseId, String testId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return null;

        Test test = testRepository.findById(testId).orElse(null);
        if (test == null) return null;

        if (!course.getTests().contains(test)) {
            course.getTests().add(test);
            courseRepository.save(course);
        }
        return course;
    }

    public List<Test> getTestsByCourseId(String courseUuid) {
        Optional<Course> courseOpt = courseRepository.findById(courseUuid);
        Course course = courseOpt.get();
        return course.getTests().stream().toList();
    }
}