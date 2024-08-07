package me.minphoneaung.springcrud.repository;

import me.minphoneaung.springcrud.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findBySchoolId(Integer schoolId);

    Page<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrSchool_NameContainingIgnoreCase(String name, String email, String schoolName, Pageable pageable);
}