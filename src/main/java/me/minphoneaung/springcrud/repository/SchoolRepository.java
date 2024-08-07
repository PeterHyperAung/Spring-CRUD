package me.minphoneaung.springcrud.repository;

import me.minphoneaung.springcrud.entities.School;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Integer> {
    List<School> findByNameContainingIgnoreCaseOrPrincipalContainingIgnoreCase(String name, String principal, PageRequest pageRequest);
    long countByNameContainingIgnoreCase(String name);
}
