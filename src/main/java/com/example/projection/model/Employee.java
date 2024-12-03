package com.example.projection.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must be field")
    private String firstName;
    @NotBlank(message = "Last name must be field")
    private String lastName;
    @NotBlank(message = "Position must be field")
    private String position;
    @NotNull(message = "Salary must be field")
    @Positive(message = "Salary can't be less then 0")
    private Double salary;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "department_id")
    private Department department;
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getDepartmentName() {
        return department != null ? department.getName() : null;
    }

}
