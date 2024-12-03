package com.example.projection.service;

import com.example.projection.exceptions.EmployeeNotFoundException;
import com.example.projection.model.Employee;
import com.example.projection.projections.EmployeeProjection;
import com.example.projection.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() ->new EmployeeNotFoundException(id));
    }
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
       Employee employee = employeeRepository.findById(id).orElseThrow(() ->new EmployeeNotFoundException(id));
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setPosition(employeeDetails.getPosition());
            employee.setSalary(employeeDetails.getSalary());
            employee.setDepartment(employeeDetails.getDepartment());
            return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    public List<EmployeeProjection> getEmployeeProjections() {
        return employeeRepository.findAllEmployeeProjections();
    }
}
