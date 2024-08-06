package me.minphoneaung.springcrud;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import me.minphoneaung.springcrud.entities.School;
import me.minphoneaung.springcrud.entities.Student;
import me.minphoneaung.springcrud.repository.SchoolRepository;
import me.minphoneaung.springcrud.repository.StudentRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;

@AllArgsConstructor
@SpringBootApplication
public class SpringcrudApplication {

    private StudentRepository studentRepository;
    private SchoolRepository schoolRepository;

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
            var studentsList = new ArrayList<Student>();

            for (int i = 1; i <= schoolSeedingCount; i++) {
                schoolsList.add(createSchool("School " + i, "Principal " + i));
            }

            for (int i = 1; i <= 45; i++) {
                studentsList.add(createStudent("Student " + i, "student" + i + "@gmail.com",
                        LocalDate.now().minusMonths(i - 1),
                        LocalDate.now().minusMonths(i),
                        schoolsList.get(i % schoolSeedingCount)
                ));
            }

            System.out.println("SEEDING DONE!");
        };
    }

    private School createSchool(String name, String principal) {
        var school = new School();
        school.setName(name);
        school.setPrincipal(principal);
        return schoolRepository.save(school);
    }

    private Student createStudent(
            String name, String email, LocalDate dob,
            LocalDate startedAt, School school) {

        var student = new Student();
        student.setName(name);
        student.setEmail(email);
        student.setDateOfBirth(dob);
        student.setStartedAt(startedAt);
        student.setSchool(school);
        return studentRepository.save(student);
    }

}
