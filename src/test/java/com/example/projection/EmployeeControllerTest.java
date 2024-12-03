package com.example.projection;

import com.example.projection.controller.EmployeeController;
import com.example.projection.model.Employee;
import com.example.projection.projections.EmployeeProjection;
import com.example.projection.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {
    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Инициализация MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getAllEmployees_ShouldReturnEmployees() throws Exception {
        // Given
        Employee employee1 = new Employee(1L, "John", "Doe", "Developer", 70000.0, null);
        Employee employee2 = new Employee(2L, "Jane", "Smith", "Manager", 90000.0, null);
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(employee1, employee2));

        // When & Then
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() throws Exception {
        // Given
        Employee employee = new Employee(1L, "John", "Doe", "Developer", 70000.0, null);
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        // When & Then
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }


    @Test
    void createEmployee_ShouldCreateEmployee() throws Exception {
        // Given
        Employee createdEmployee = new Employee(1L, "John", "Doe", "Developer", 70000.0, null);
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(createdEmployee);

        // When & Then
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"position\":\"Developer\", \"salary\":70000.0}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.position").value("Developer"));
    }

    @Test
    void updateEmployee_ShouldUpdateEmployee() throws Exception {
        // Given
        Employee updatedEmployee = new Employee(1L, "John", "Doe", "Senior Developer", 80000.0, null);
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updatedEmployee);

        // When & Then
        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"position\":\"Senior Developer\", \"salary\":80000.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value("Senior Developer"))
                .andExpect(jsonPath("$.salary").value(80000.0));
    }

    @Test
    void deleteEmployee_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    void getEmployeeProjections_ShouldReturnEmployeeProjections() throws Exception {
        // Given
        EmployeeProjection projection = new EmployeeProjection() {
            @Override
            public String getFullName() {
                return "John Doe";
            }

            @Override
            public String getPosition() {
                return "Developer";
            }

            @Override
            public String getDepartmentName() {
                return "IT";
            }
        };

        when(employeeService.getEmployeeProjections()).thenReturn(Arrays.asList(projection));

        // When & Then
        mockMvc.perform(get("/api/employees/projections"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$[0].position").value("Developer"))
                .andExpect(jsonPath("$[0].departmentName").value("IT"));
    }
}
