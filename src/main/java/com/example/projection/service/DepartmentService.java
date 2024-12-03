package com.example.projection.service;

import com.example.projection.exceptions.DepartmentNotFoundException;
import com.example.projection.model.Department;
import com.example.projection.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));
    }

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = getDepartmentById(id);

        department.setName(departmentDetails.getName());
        return departmentRepository.save(department);
    }
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}