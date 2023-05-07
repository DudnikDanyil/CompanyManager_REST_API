package com.example.CompanyManagerApplication.controllers;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.services.ProjectEmployeeService;
import com.example.CompanyManagerApplication.services.ServiceInterface;
import com.example.CompanyManagerApplication.services.impl.EmployeeServiceImpl;
import com.example.CompanyManagerApplication.services.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ServiceInterface serviceInterface;
    private final ProjectServiceImpl projectService;
    private final EmployeeServiceImpl employeeService;

    private final ProjectEmployeeService projectEmployeeService;

    @Autowired
    public ProjectController(@Qualifier("projectServiceImpl") ServiceInterface serviceInterface,
                             ProjectServiceImpl projectService, EmployeeServiceImpl employeeService, ProjectEmployeeService projectEmployeeService) {
        this.serviceInterface = serviceInterface;
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.projectEmployeeService = projectEmployeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO projectDTO) {

        ProjectDTO savedProjectDTO = (ProjectDTO) serviceInterface.save(projectDTO);
        return ResponseEntity.ok(savedProjectDTO);
    }

    @DeleteMapping("/delete")
    public HttpStatus delete(@RequestParam("id") Long id) {

        serviceInterface.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping("/get/all")
    public List<EmployeeDTO> getAll() {

        return serviceInterface.getAll();
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProjectDTO> updateEmployee(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {

        ProjectDTO updatedProject = (ProjectDTO) serviceInterface.update(id, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/{projectId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployeesForProject(@PathVariable Long projectId) {

        List<EmployeeDTO> employeeDTOList = projectService.getAllEmployeesForProject(projectId);
        return new ResponseEntity<>(employeeDTOList, HttpStatus.OK);
    }

    @GetMapping("/{projectId}/available-employees")
    public List<EmployeeDTO> getAvailableEmployeesForProject(@PathVariable Long projectId) {

        return projectService.getAvailableEmployee(projectId);
    }

    @PostMapping("/{employeeId}/add/{projectId}")
    public HttpStatus addProjectToEmployee(@PathVariable Long employeeId, @PathVariable Long projectId) {

        projectEmployeeService.addEntityToAssociation(employeeId, projectId);
        return HttpStatus.OK;
    }

    @DeleteMapping("/{employeeId}/remove/{projectId}")
    public HttpStatus removeEmployeeFromProject(@PathVariable Long employeeId, @PathVariable Long projectId) {

        projectEmployeeService.removeEntityFromAssociation(employeeId, projectId);
        return HttpStatus.OK;
    }
}
