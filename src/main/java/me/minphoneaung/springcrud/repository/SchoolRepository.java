package me.minphoneaung.springcrud.repository;

import me.minphoneaung.springcrud.entities.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    Page<School> findByNameContainingIgnoreCaseOrPrincipalContainingIgnoreCase(String name, String principal, PageRequest pageRequest);
}
