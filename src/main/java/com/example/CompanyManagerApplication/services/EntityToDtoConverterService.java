package com.example.CompanyManagerApplication.services;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import org.modelmapper.MappingException;
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

        try {
            List<ProjectDTO> projectDTOList = new ArrayList<>();
            for (Project project : projectList) {
                projectDTOList.add(convertToProjectDTO(project));
            }
            return projectDTOList;

        } catch (MappingException e) {
            throw new IllegalArgumentException("Error converting project list to DTO list", e);
        }
    }

    public List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> employeeList) {

        try {
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();
            for (Employee employee : employeeList) {
                employeeDTOList.add(convertToEmployeeDTO(employee));
            }
            return employeeDTOList;
        } catch (MappingException e) {
            throw new IllegalArgumentException("Error converting employee list to DTO list", e);
        }
    }

    public ProjectDTO convertToProjectDTO(Project project) {

        try {
            return modelMapper.map(project, ProjectDTO.class);
        } catch (MappingException e) {
            throw new IllegalArgumentException("Error converting project to DTO", e);
        }
    }

    public Project convertToProject(ProjectDTO projectDTO) {

        try {
            return modelMapper.map(projectDTO, Project.class);
        } catch (MappingException e) {
            throw new IllegalArgumentException("Error converting DTO to project", e);
        }
    }

    public EmployeeDTO convertToEmployeeDTO(Employee employee) {

        try {
            return modelMapper.map(employee, EmployeeDTO.class);
        } catch (MappingException e) {
            throw new IllegalArgumentException("Error converting employee to DTO", e);
        }
    }

    public Employee convertToEmployee(EmployeeDTO employeeDTO) {

        try {
            return modelMapper.map(employeeDTO, Employee.class);
        } catch (MappingException e) {
            throw new IllegalArgumentException("Error converting DTO to employee", e);
        }
    }
}
