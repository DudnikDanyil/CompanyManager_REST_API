package com.example.CompanyManagerApplication.controllers;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.services.ServiceInterface;
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

    @Autowired
    public ProjectController(@Qualifier("projectServiceImpl") ServiceInterface serviceInterface,
                             ProjectServiceImpl projectService) {
        this.serviceInterface = serviceInterface;
        this.projectService = projectService;
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
}
