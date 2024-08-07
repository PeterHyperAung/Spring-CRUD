package me.minphoneaung.springcrud.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class School {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String principal;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Student> students;
}
