package me.minphoneaung.springcrud;

import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.entities.User;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.repository.StudentRepository;
import me.minphoneaung.springcrud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@SpringBootApplication
public class SpringcrudApplication {

    private StudentRepository studentRepository;
    private SchoolRepository schoolRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringcrudApplication.class, args);
    }

    // FOR DATABASE SEEDING
    private final int schoolSeedingCount = 25;
    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner() {
        return (String... args) -> {
            var schoolsList = new ArrayList<School>();

            for (int i = 1; i <= schoolSeedingCount; i++) {
                schoolsList.add(createSchool("School " + i, "Principal " + i));
            }

            for (int i = 1; i <= 45; i++) {
                createStudent("Student " + i, "student" + i + "@gmail.com",
                        LocalDate.now().minusMonths(i - 1),
                        LocalDate.now().minusMonths(i),
                        schoolsList.get(i % schoolSeedingCount)
                );
            }

            createAdminUser();

            System.out.println("SEEDING DONE!");
        };
    }

    private School createSchool(String name, String principal) {
        var school = new School();
        school.setName(name);
        school.setPrincipal(principal);
        return schoolRepository.save(school);
    }

    private void createStudent(
            String name, String email, LocalDate dob,
            LocalDate startedAt, School school) {

        var student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setDateOfBirth(dob);
        student.setStartedAt(startedAt);
        student.setSchool(school);
        studentRepository.save(student);
    }

    private void createAdminUser() {
        var user = new User();
        user.setUsername("super admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRoleToAdmin();
        userRepository.save(user);
    }

}
