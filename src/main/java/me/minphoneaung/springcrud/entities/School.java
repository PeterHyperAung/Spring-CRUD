package me.minphoneaung.springcrud.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.minphoneaung.springcrud.entities.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class School {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "school", cascade = CascadeType.PERSIST)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JsonBackReference
    private List<Student> students;
}
