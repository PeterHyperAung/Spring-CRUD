package me.minphoneaung.springcrud.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = true)
    @JsonManagedReference
    private School school;

    private LocalDate startedAt;

}
