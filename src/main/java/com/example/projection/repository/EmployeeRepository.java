package com.example.projection.repository;

import com.example.projection.model.Employee;
import com.example.projection.projections.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e")
    List<EmployeeProjection> findAllEmployeeProjections();
}
