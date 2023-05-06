package com.example.CompanyManagerApplication.services;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityToDtoConverterService {
    private final ModelMapper modelMapper;

    @Autowired
    public EntityToDtoConverterService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<ProjectDTO> convertToProjectDTOList(List<Project> projectList) {

        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (Project project : projectList) {
            projectDTOList.add(convertToProjectDTO(project));
        }
        return projectDTOList;
    }

    public List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> employeeList) {

        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee employee : employeeList) {
            employeeDTOList.add(convertToEmployeeDTO(employee));
        }
        return employeeDTOList;
    }

    public ProjectDTO convertToProjectDTO(Project project) {

        return modelMapper.map(project, ProjectDTO.class);
    }

    public Project convertToProject(ProjectDTO projectDTO) {

        return modelMapper.map(projectDTO, Project.class);
    }

    public EmployeeDTO convertToEmployeeDTO(Employee employee) {

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee convertToEmployee(EmployeeDTO employeeDTO) {

        return modelMapper.map(employeeDTO, Employee.class);
    }
}
