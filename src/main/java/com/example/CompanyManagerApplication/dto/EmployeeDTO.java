package com.example.CompanyManagerApplication.dto;

import com.example.CompanyManagerApplication.models.enums.EmployeeLevel;
import com.example.CompanyManagerApplication.models.enums.EmployeeType;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private EmployeeLevel level;
    private String position;
    private EmployeeType type;

}
