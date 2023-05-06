package com.example.CompanyManagerApplication.services;

import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import com.example.CompanyManagerApplication.repositories.EmployeeRepository;
import com.example.CompanyManagerApplication.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProjectEmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectEmployeeService(EmployeeRepository employeeRepository,
                                  ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    public void addEntityToAssociation(Long employeeId, Long projectId) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {

            Optional<Project> optionalProject = projectRepository.findById(projectId);
            if (optionalProject.isPresent()) {

                Employee employee = optionalEmployee.get();
                Project project = optionalProject.get();

                employee.getProjects().add(project);
                employeeRepository.save(employee);
            } else {
                throw new EntityNotFoundException("Project not found with id: " + projectId);
            }
        } else {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }
    }
}
