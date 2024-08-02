package me.minphoneaung.springcrud.students;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.minphoneaung.springcrud.schools.School;
import org.hibernate.annotations.OnDelete;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


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

    private Integer age;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = true)
    @JsonManagedReference
    private School school;

    private LocalDate startedAt;

}
