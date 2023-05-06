package com.example.CompanyManagerApplication.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name project cannot be null")
    @Size(min = 2, max = 200, message = "Name project must be between 2 and 200 characters")
    private String name;

    @Column(name = "customer_name")
    @NotNull(message = "Customer name cannot be null")
    @Size(min = 2, max = 200, message = "Customer name must be between 2 and 200 characters")
    private String customerName;

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees = new ArrayList<>();

}