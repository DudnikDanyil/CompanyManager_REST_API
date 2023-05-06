package com.example.CompanyManagerApplication.services;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ServiceInterface<T> {
    public T save(T entityDTO);
    public void delete(Long id);
    List<T> getAll();
    T update(Long id, T entityDTO);
}
