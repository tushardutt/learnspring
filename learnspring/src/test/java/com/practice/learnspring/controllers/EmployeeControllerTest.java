package com.practice.learnspring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.learnspring.entities.Employee;
import com.practice.learnspring.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @InjectMocks
    EmployeeController employeeController;
    @MockBean
    EmployeeService employeeService;
    private static final String URI = "/api/employees/";
    private static final Long EMPLOYEE_ID = 101L;
    private static final String EMPLOYEE_NAME = "Germano";
    private static final String EMPLOYEE_DEPT = "IT";
    private static final String EMPLOYEE_ADDRESS = "A-212, Model Town Ludihana, Punjab, India";
    private static final String EMPLOYEE_EMAIL = "germano@gmail.com";
    private static final Long EMPLOYEE_MOBILE = 89845L;
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetEmployees() throws Exception {
        //Test get all employees
        Employee employee =  getEmployee();
        List<Employee> emp = new ArrayList<>();
        emp.add(employee);
        Mockito.when(employeeService.getEmployees()).thenReturn(emp);
        mockMvc.perform(MockMvcRequestBuilders.get(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].name").value(employee.getName())).andReturn();
    }

    @Test
    public void testGetEmployee() throws Exception {
        //Test get employee by id
        Employee employee = getEmployee();
        Long empId = employee.getId();
        Mockito.when(employeeService.getEmployee(empId)).thenReturn(employee);
        mockMvc.perform(MockMvcRequestBuilders.get(URI + empId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(employee.getId())).andReturn();
    }

    @Test
    public void testAddEmployee() throws Exception {
        // Setup
        // Configure EmployeeService.addEmployee(...).
        Employee employee = getEmployee();
        Mockito.when(employeeService.addEmployee(employee)).thenReturn(employee);
        // Run the test
        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                        .content(asJsonString(employee)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andReturn().getResponse();

    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // Setup
        // Configure EmployeeService.updateEmployee(...).
        Employee employee = getEmployee();
        Mockito.when(employeeService.updateEmployee(employee,employee.getId())).thenReturn(employee);

        // Run the test
        mockMvc.perform(MockMvcRequestBuilders.put(URI+EMPLOYEE_ID)
                        .content(asJsonString(employee)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andReturn().getResponse();
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        // Setup
        Mockito.when(employeeService.deleteEmployee(EMPLOYEE_ID)).thenReturn(true);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete(URI + EMPLOYEE_ID)
                        .accept(MediaType.APPLICATION_JSON))
                //.andExpect(status().isOk())
                .andReturn().getResponse();
        assertEquals("Data deleted successfully", response.getContentAsString());
    }

    @Test
    public void testUpdateEmployeeName() throws Exception {
        // Setup
        // Configure EmployeeService.updateEmployeeName(...).
        Employee employee = getEmployee();
        final ResponseEntity<Employee> employeeResponseEntity = new ResponseEntity<>(employee, HttpStatus.OK);
        Mockito.when(employeeService.updateEmployeeName(EMPLOYEE_ID, employee)).thenReturn(employeeResponseEntity);
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.patch(URI + EMPLOYEE_ID)
                        .content(asJsonString(employee)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
       assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    public Employee getEmployee(){
        Employee employee = new Employee(EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_DEPT, EMPLOYEE_ADDRESS, EMPLOYEE_EMAIL, EMPLOYEE_MOBILE);
        return employee;
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
