package com.example.CompanyManagerApplication.models;

import com.example.CompanyManagerApplication.models.enums.EmployeeLevel;
import com.example.CompanyManagerApplication.models.enums.EmployeeType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 200, message = "First name must be between 2 and 200 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 200, message = "Last name must be between 2 and 200 characters")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    @NotNull(message = "Level cannot be null")
    private EmployeeLevel level;

    @Column(name = "position")
    @NotNull(message = "Position cannot be null")
    @Size(min = 2, max = 200, message = "Last name must be between 2 and 200 characters")
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull(message = "Type cannot be null")
    private EmployeeType type;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "project_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects = new ArrayList<>();

}