package com.example.CompanyManagerApplication.services;

import com.example.CompanyManagerApplication.dto.ProjectDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ServiceInterfaceDTO<T> {
    public T save(T entityDTO);
    public void delete(Long id);
    List<T> getAll();
    T update(Long id, T entityDTO);
}
