package com.example.CompanyManagerApplication.services.impl;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import com.example.CompanyManagerApplication.repositories.EmployeeRepository;
import com.example.CompanyManagerApplication.services.EntityToDtoConverterService;
import com.example.CompanyManagerApplication.services.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements ServiceInterface<EmployeeDTO> {
    private final EmployeeRepository employeeRepository;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EntityToDtoConverterService entityToDtoConverterService) {
        this.employeeRepository = employeeRepository;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) {

        Employee savedEmployee = employeeRepository.save(entityToDtoConverterService.convertToEmployee(employeeDTO));
        return entityToDtoConverterService.convertToEmployeeDTO(savedEmployee);
    }

    @Override
    public void delete(Long id) {

        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> getAll() {

        return entityToDtoConverterService.convertToEmployeeDTOList(employeeRepository.findAll());
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
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

}


