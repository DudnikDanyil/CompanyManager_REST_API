package com.example.CompanyManagerApplication.services.impl;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.exceptions.EntityAlreadyExistsException;
import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import com.example.CompanyManagerApplication.repositories.EmployeeRepository;
import com.example.CompanyManagerApplication.repositories.ProjectRepository;
import com.example.CompanyManagerApplication.services.EntityToDtoConverterService;
import com.example.CompanyManagerApplication.services.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ServiceInterface<ProjectDTO> {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              EmployeeRepository employeeRepository, EntityToDtoConverterService entityToDtoConverterService) {
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    @Transactional
    public ProjectDTO save(ProjectDTO projectDTO) {

        try {
            Project savedProject = projectRepository.save(entityToDtoConverterService.convertToProject(projectDTO));
            return entityToDtoConverterService.convertToProjectDTO(savedProject);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException("Project with the same ID or email already exists.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {

        try {
            projectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
    }

    @Override
    public List<ProjectDTO> getAll() {

        return entityToDtoConverterService.convertToProjectDTOList(projectRepository.findAll());
    }

    @Override
    @Transactional
    public ProjectDTO update(Long id, ProjectDTO projectDTO) {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            updateProject(project, projectDTO);
            projectRepository.save(project);
            return entityToDtoConverterService.convertToProjectDTO(project);
        } else {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
    }

    public List<EmployeeDTO> getAllEmployeesForProject(Long projectId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            List<Employee> employeeList = project.getEmployees();

            return entityToDtoConverterService.convertToEmployeeDTOList(employeeList);
        } else {
            throw new EntityNotFoundException("Project not found with id: " + projectId);
        }
    }

    public List<EmployeeDTO> getAvailableEmployee(Long projectId) {

        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            List<Employee> availableEmployeeList = employeeRepository.findByProjectsNotContaining(
                    optionalProject.get());

            return entityToDtoConverterService.convertToEmployeeDTOList(availableEmployeeList);
        } else {
            throw new EntityNotFoundException("Employee not found with id: " + projectId);
        }
    }

    private void updateProject(Project project, ProjectDTO projectDTO) {

        if (projectDTO.getName() != null) {
            project.setName(projectDTO.getName());
        }
        if (projectDTO.getCustomerName() != null) {
            project.setCustomerName(projectDTO.getCustomerName());
        }
    }
}
