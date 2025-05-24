package src.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import src.models.Professor;
import src.models.Student;
import src.repositories.ProfessorRepository;
import src.repositories.StudentRepository;

@Service
public class CombinedUserDetailsService implements UserDetailsService {

    private final ProfessorRepository professorRepository;
    private final StudentRepository studentRepository;

    public CombinedUserDetailsService(ProfessorRepository professorRepository,
                                      StudentRepository studentRepository) {
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Professor professor = professorRepository.findByLogin(login).orElse(null);
        if (professor != null) {
            return User.builder()
                    .username(professor.getLogin())
                    .password(professor.getPassword())
                    .roles("PROFESSOR")
                    .build();
        }

        Student student = studentRepository.findByLogin(login).orElse(null);
        if (student != null) {
            return User.builder()
                    .username(student.getLogin())
                    .password(student.getPassword())
                    .roles("STUDENT")
                    .build();
        }

        throw new UsernameNotFoundException("Пользователь не найден: " + login);
    }

}
