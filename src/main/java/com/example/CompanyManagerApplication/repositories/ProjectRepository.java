package com.example.CompanyManagerApplication.repositories;

import com.example.CompanyManagerApplication.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
