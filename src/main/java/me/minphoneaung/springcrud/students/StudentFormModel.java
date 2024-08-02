package me.minphoneaung.springcrud.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFormModel {
    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private Integer schoolId;
}
