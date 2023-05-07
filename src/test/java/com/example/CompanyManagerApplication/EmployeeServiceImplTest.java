package com.example.CompanyManagerApplication;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import com.example.CompanyManagerApplication.models.enums.EmployeeLevel;
import com.example.CompanyManagerApplication.models.enums.EmployeeType;
import com.example.CompanyManagerApplication.repositories.EmployeeRepository;
import com.example.CompanyManagerApplication.repositories.ProjectRepository;
import com.example.CompanyManagerApplication.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EmployeeServiceImplTest {

    private EmployeeServiceImpl employeeService;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    @PersistenceContext
    private EntityManager entityManager;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private Project project;

    @Autowired
    public EmployeeServiceImplTest(EmployeeServiceImpl employeeService,
                                   EmployeeRepository employeeRepository,
                                   ProjectRepository projectRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }


    @BeforeEach
    public void createEmployee() {
        this.employee = new Employee();
        this.employee.setFirstName("Test1");
        this.employee.setLastName("Test1");
        this.employee.setLevel(EmployeeLevel.valueOf("JUNIOR"));
        this.employee.setPosition("Test1");
        this.employee.setType(EmployeeType.valueOf("NON_MANAGER"));
    }

    @BeforeEach
    public void createEmployeeDTO() {
        this.employeeDTO = new EmployeeDTO();
        this.employeeDTO.setFirstName("Test1");
        this.employeeDTO.setLastName("Test1");
        this.employeeDTO.setLevel(EmployeeLevel.valueOf("JUNIOR"));
        this.employeeDTO.setPosition("Test1");
        this.employeeDTO.setType(EmployeeType.valueOf("NON_MANAGER"));
    }

    @BeforeEach
    public void createProject() {
        this.project = new Project();
        this.project.setName("Test_Project_3");
        this.project.setCustomerName("Test_Customer_Name_3");
    }

    @Test
    public void testSave() {

        EmployeeDTO savedEmployeeDTO = employeeService.save(employeeDTO);

        assertNotNull(savedEmployeeDTO.getId());
        assertEquals(employeeDTO.getFirstName(), savedEmployeeDTO.getFirstName());
        assertEquals(employeeDTO.getLastName(), savedEmployeeDTO.getLastName());
        assertEquals(employeeDTO.getLevel(), savedEmployeeDTO.getLevel());
        assertEquals(employeeDTO.getPosition(), savedEmployeeDTO.getPosition());
        assertEquals(employeeDTO.getType(), savedEmployeeDTO.getType());
    }

    @Test
    public void testDelete() {

        EmployeeDTO savedEmployeeDTO = employeeService.save(employeeDTO);

        employeeService.delete(savedEmployeeDTO.getId());

        assertFalse(employeeRepository.findById(savedEmployeeDTO.getId()).isPresent());
    }

    @Test
    public void testUpdate() {

        EmployeeDTO savedEmployeeDTO = employeeService.save(employeeDTO);
        Long id = savedEmployeeDTO.getId();

        EmployeeDTO updatedEmployeeDTO = new EmployeeDTO();
        updatedEmployeeDTO.setFirstName("Test1");
        updatedEmployeeDTO.setLastName("Test1");
        updatedEmployeeDTO.setLevel(EmployeeLevel.valueOf("JUNIOR"));
        updatedEmployeeDTO.setPosition("Test1");
        updatedEmployeeDTO.setType(EmployeeType.valueOf("NON_MANAGER"));

        EmployeeDTO result = employeeService.update(id, updatedEmployeeDTO);

        assertEquals(id, result.getId());
        assertEquals("Test1", result.getFirstName());
        assertEquals("Test1", result.getLastName());
        assertEquals(EmployeeLevel.valueOf("JUNIOR"), result.getLevel());
        assertEquals("Test1", result.getPosition());
        assertEquals(EmployeeType.valueOf("NON_MANAGER"), result.getType());
    }


    @Test
    @Transactional
    void testGetAllProjectsForEmployee() {

        entityManager.persist(project);

        employee.setProjects(Arrays.asList(project));
        entityManager.persist(employee);
        ;

        List<ProjectDTO> projectDTOList = employeeService.getAllProjectsForEmployee(employee.getId());

        assertEquals(1, projectDTOList.size());
        assertEquals(project.getName(), projectDTOList.get(0).getName());
    }

    @Test
    void testGetAvailableProjects() {

        Project project2 = new Project();
        project2.setName("Test_Project_2");
        project2.setCustomerName("Test_Customer_Name_2");

        projectRepository.saveAll(Arrays.asList(project, project2));
        employeeRepository.saveAll(Arrays.asList(employee));

        project.setEmployees(Arrays.asList(employee));

        List<ProjectDTO> projectDTOList = employeeService.getAvailableProjects(employee.getId());

        assertEquals(project2.getName(), projectDTOList.get(0).getName());
    }
}
