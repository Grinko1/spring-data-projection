package com.example.projection;

import com.example.projection.controller.DepartmentController;
import com.example.projection.model.Department;
import com.example.projection.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {
    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Инициализация MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    void getAllDepartments_ShouldReturnDepartments() throws Exception {
        // Given
        Department department1 = new Department(1L, "HR");
        Department department2 = new Department(2L, "IT");
        when(departmentService.getAllDepartments()).thenReturn(Arrays.asList(department1, department2));

        // When & Then
        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("HR"))
                .andExpect(jsonPath("$[1].name").value("IT"));
    }

    @Test
    void getDepartmentById_ShouldReturnDepartment() throws Exception {
        // Given
        Department department = new Department(1L, "HR");
        when(departmentService.getDepartmentById(1L)).thenReturn(department);

        // When & Then
        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HR"));
    }


    @Test
    void createDepartment_ShouldCreateDepartment() throws Exception {
        // Given
        Department department = new Department(1L, "Finance");
        when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

        // When & Then
        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Finance\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Finance"));
    }

    @Test
    void updateDepartment_ShouldUpdateDepartment() throws Exception {
        // Given
        Department updatedDepartment = new Department(1L, "Operations");
        when(departmentService.updateDepartment(eq(1L), any(Department.class))).thenReturn(updatedDepartment);

        // When & Then
        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Operations\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Operations"));
    }

    @Test
    void deleteDepartment_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());

        // Verify that delete method in service was called
        verify(departmentService, times(1)).deleteDepartment(1L);
    }
}
