package com.example.CompanyManagerApplication.controllers;

import com.example.CompanyManagerApplication.dto.EmployeeDTO;
import com.example.CompanyManagerApplication.services.ServiceInterfaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private final ServiceInterfaceDTO serviceInterfaceDTO;

    @Autowired
    public EmployeeController(@Qualifier("employeeServiceImpl") ServiceInterfaceDTO serviceInterfaceDTO) {
        this.serviceInterfaceDTO = serviceInterfaceDTO;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO savedEmployee = (EmployeeDTO) serviceInterfaceDTO.save(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
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
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO updatedEmployee = (EmployeeDTO) serviceInterfaceDTO.update(id, employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }
}
