package com.example.CompanyManagerApplication;

import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import com.example.CompanyManagerApplication.models.enums.EmployeeLevel;
import com.example.CompanyManagerApplication.models.enums.EmployeeType;
import com.example.CompanyManagerApplication.repositories.EmployeeRepository;
import com.example.CompanyManagerApplication.repositories.ProjectRepository;
import com.example.CompanyManagerApplication.services.ProjectEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProjectEmployeeServiceTest {

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private ProjectEmployeeService projectEmployeeService;
    private Employee employee;
    private Project project;

    @Autowired
    public ProjectEmployeeServiceTest(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ProjectEmployeeService projectEmployeeService) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.projectEmployeeService = projectEmployeeService;
    }

    @BeforeEach
    public void createEmployee() {
        this.employee = new Employee();
        this.employee.setFirstName("Test5");
        this.employee.setLastName("Test5");
        this.employee.setLevel(EmployeeLevel.valueOf("JUNIOR"));
        this.employee.setPosition("Test5");
        this.employee.setType(EmployeeType.valueOf("NON_MANAGER"));
    }

    @BeforeEach
    public void createProject() {
        this.project = new Project();
        this.project.setName("Test_Project_5");
        this.project.setCustomerName("Test_Customer_Name_5");
    }

    @Test
    @Transactional
    void testAddEntityToAssociation() {

        employeeRepository.save(employee);
        projectRepository.save(project);

        projectEmployeeService.addEntityToAssociation(employee.getId(), project.getId());

        Optional<Employee> optionalEmployee = employeeRepository.findById(employee.getId());
        assertTrue(optionalEmployee.isPresent());
        Employee savedEmployee = optionalEmployee.get();
        assertEquals(1, savedEmployee.getProjects().size());
        assertEquals(project.getId(), savedEmployee.getProjects().get(0).getId());
    }

    @Test
    void testRemoveEntityFromAssociation() {

        employee.getProjects().add(project);
        project.getEmployees().add(employee);
        employeeRepository.save(employee);
        projectRepository.save(project);

        assertTrue(employee.getProjects().contains(project));
        assertTrue(project.getEmployees().contains(employee));

        projectEmployeeService.removeEntityFromAssociation(employee.getId(), project.getId());

        Employee updatedEmployee = employeeRepository.findById(employee.getId()).orElseThrow();
        assertFalse(updatedEmployee.getProjects().contains(project));

        Project updatedProject = projectRepository.findById(project.getId()).orElseThrow();
        assertFalse(updatedProject.getEmployees().contains(employee));
    }

}
