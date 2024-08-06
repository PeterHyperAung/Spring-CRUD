package me.minphoneaung.springcrud.repository;

import me.minphoneaung.springcrud.entities.Student;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findBySchoolId(Integer schoolId);

    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSchool_NameContainingIgnoreCase(String name, String email, String schoolName, PageRequest pageRequest);

    long countByNameContainingIgnoreCase(String name);
}