package me.minphoneaung.springcrud.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class Student {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private LocalDate dateOfBirth;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    @JsonManagedReference
    private School school;

    private LocalDate startedAt;

}
