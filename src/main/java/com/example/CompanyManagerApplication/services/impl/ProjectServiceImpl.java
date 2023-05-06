package com.example.CompanyManagerApplication.services.impl;

import com.example.CompanyManagerApplication.dto.ProjectDTO;
import com.example.CompanyManagerApplication.models.Employee;
import com.example.CompanyManagerApplication.models.Project;
import com.example.CompanyManagerApplication.repositories.ProjectRepository;
import com.example.CompanyManagerApplication.services.EntityToDtoConverterService;
import com.example.CompanyManagerApplication.services.ServiceInterfaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ServiceInterfaceDTO<ProjectDTO> {
    private final ProjectRepository projectRepository;
    private final EntityToDtoConverterService entityToDtoConverterService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              EntityToDtoConverterService entityToDtoConverterService) {
        this.projectRepository = projectRepository;
        this.entityToDtoConverterService = entityToDtoConverterService;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) {

        Project savedProject = projectRepository.save(entityToDtoConverterService.convertToProject(projectDTO));
        return entityToDtoConverterService.convertToProjectDTO(savedProject);
    }

    @Override
    public void delete(Long id) {

        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectDTO> getAll() {

        return entityToDtoConverterService.convertToProjectDTOList(projectRepository.findAll());
    }

    @Override
    public ProjectDTO update(Long id, ProjectDTO projectDTO) {

        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            if (projectDTO.getName() != null) {
                project.setName(projectDTO.getName());
            }
            if (projectDTO.getCustomerName() != null) {
                project.setCustomerName(projectDTO.getCustomerName());
            }
            projectRepository.save(project);
            return entityToDtoConverterService.convertToProjectDTO(project);
        } else {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
    }

}
