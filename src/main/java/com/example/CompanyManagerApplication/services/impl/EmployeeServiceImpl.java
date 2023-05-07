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
public class EmployeeServiceImpl implements ServiceInterface<EmployeeDTO> {
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               ProjectRepository projectRepository,
                               EntityToDtoConverterService entityToDtoConverterService) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    @Transactional
    public EmployeeDTO save(EmployeeDTO employeeDTO) {

        try {
            Employee savedEmployee = employeeRepository.save(entityToDtoConverterService.convertToEmployee(employeeDTO));
            return entityToDtoConverterService.convertToEmployeeDTO(savedEmployee);
        } catch (DataIntegrityViolationException ex) {
            throw new EntityAlreadyExistsException("Employee with the same ID or email already exists.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {

        try {
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
    }

    @Override
    public List<EmployeeDTO> getAll() {

        return entityToDtoConverterService.convertToEmployeeDTOList(employeeRepository.findAll());
    }

    @Override
    @Transactional
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            updateEmployee(employee, employeeDTO);
            employeeRepository.save(employee);
            return entityToDtoConverterService.convertToEmployeeDTO(employee);
        } else {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
    }

    public List<ProjectDTO> getAllProjectsForEmployee(Long employeeId) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Project> projectList = employee.getProjects();

            return (entityToDtoConverterService.convertToProjectDTOList(projectList));
        } else {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }
    }


    public List<ProjectDTO> getAvailableProjects(Long employeeId) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            List<Project> availableProjectList = projectRepository.findByEmployeesNotContaining(
                    optionalEmployee.get());

            return entityToDtoConverterService.convertToProjectDTOList(availableProjectList);
        } else {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }
    }

    private void updateEmployee(Employee employee, EmployeeDTO employeeDTO) {

        if (employeeDTO.getFirstName() != null) {
            employee.setFirstName(employeeDTO.getFirstName());
        }
        if (employeeDTO.getLastName() != null) {
            employee.setLastName(employeeDTO.getLastName());
        }
        if (employeeDTO.getLevel() != null) {
            employee.setLevel(employeeDTO.getLevel());
        }
        if (employeeDTO.getPosition() != null) {
            employee.setPosition(employeeDTO.getPosition());
        }
        if (employeeDTO.getType() != null) {
            employee.setType(employeeDTO.getType());
        }
    }
}

