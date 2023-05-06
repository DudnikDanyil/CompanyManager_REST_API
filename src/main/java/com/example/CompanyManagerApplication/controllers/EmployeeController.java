package com.example.CompanyManagerApplication.controllers;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.services.ServiceInterface;
import com.example.CompanyManagerApplication.services.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final ServiceInterface serviceInterface;
    private final EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeController(@Qualifier("employeeServiceImpl") ServiceInterface serviceInterface,
                              EmployeeServiceImpl employeeService) {
        this.serviceInterface = serviceInterface;
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO savedEmployee = (EmployeeDTO) serviceInterface.save(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
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
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO updatedEmployee = (EmployeeDTO) serviceInterface.update(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{employeeId}/projects")
    public ResponseEntity<List<ProjectDTO>> getAllProjectsForEmployee(@PathVariable Long employeeId) {

        List<ProjectDTO> projectDTOList = employeeService.getAllProjectsForEmployee(employeeId);
        return new ResponseEntity<>(projectDTOList, HttpStatus.OK);
    }
}
