package com.example.projection.exceptions;

public class DepartmentNotFoundException extends RuntimeException{
    public DepartmentNotFoundException(Long id) {
        super("Department with id: "+ id +" not found");
    }
}
