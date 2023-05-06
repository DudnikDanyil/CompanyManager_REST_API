package com.example.CompanyManagerApplication.controllers;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.services.ServiceInterfaceDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ServiceInterfaceDTO serviceInterfaceDTO;

    public ProjectController(@Qualifier("projectServiceImpl") ServiceInterfaceDTO serviceInterfaceDTO) {
        this.serviceInterfaceDTO = serviceInterfaceDTO;
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO projectDTO) {

        ProjectDTO savedProjectDTO = (ProjectDTO) serviceInterfaceDTO.save(projectDTO);
        return ResponseEntity.ok(savedProjectDTO);
    }

    @DeleteMapping("/delete")
    public HttpStatus delete(@RequestParam("id") Long id) {

        serviceInterfaceDTO.delete(id);
        return HttpStatus.OK;
    }

    @GetMapping("/get/all")
    public List<EmployeeDTO> getAll() {

        return serviceInterfaceDTO.getAll();
    }

    @PutMapping("/change/{id}")
    public ResponseEntity<ProjectDTO> updateEmployee(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {

        ProjectDTO updatedProject = (ProjectDTO) serviceInterfaceDTO.update(id, projectDTO);
        return ResponseEntity.ok(updatedProject);
    }
}
