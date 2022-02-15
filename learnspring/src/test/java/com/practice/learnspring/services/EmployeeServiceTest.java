package com.practice.learnspring.services;

import com.practice.learnspring.entities.Employee;
import com.practice.learnspring.repo.EmployeeRepo;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;
    @Mock
    EmployeeRepo employeeRepo;
    private static final Long EMPLOYEE_ID = 101L;
    private static final String EMPLOYEE_NAME = "Germano";
    private static final String EMPLOYEE_DEPT = "IT";
    private static final String EMPLOYEE_ADDRESS = "A-212, Model Town Ludihana, Punjab, India";
    private static final String EMPLOYEE_EMAIL = "germano@gmail.com";
    private static final Long EMPLOYEE_MOBILE = 89845L;
    @Test
    public void testGetEmployees() {
        // Setup
        Employee employee = getEmployee();
        final List<Employee> expectedResult = List.of(employee);

        // Configure EmployeeRepo.findAll(...).
        final List<Employee> employees = List.of(employee);
        Mockito.when(employeeRepo.findAll()).thenReturn(employees);

        // Run the test
        final List<Employee> result = employeeService.getEmployees();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetEmployee() {
        // Setup
        Employee employee = getEmployee();
        final Employee expectedResult = getEmployee();

        // Configure EmployeeRepo.findById(...).
        final Optional<Employee> employeeOptional = Optional.of(employee);
        when(employeeRepo.findById(EMPLOYEE_ID)).thenReturn(employeeOptional);

        // Run the test
        final Employee result = employeeService.getEmployee(EMPLOYEE_ID);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testAddEmployee() {
        // Setup
        final Employee employee = getEmployee();
        final Employee expectedResult = getEmployee();
        // Configure EmployeeRepo.save(...).
        Employee employee1 = employee;
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee1);
        // Run the test
        final Employee result = employeeService.addEmployee(employee);
        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testUpdateEmployee() {
        // Setup
        final Employee employee = getEmployee();
        final Employee expectedResult = getEmployee();

        // Configure EmployeeRepo.findById(...).
        final Optional<Employee> employeeOptional = Optional.of(employee);
        Mockito.when(employeeRepo.findById(EMPLOYEE_ID)).thenReturn(employeeOptional);

        // Configure EmployeeRepo.save(...).
        final Employee employee1 = getEmployee();
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee1);

        // Run the test
        final Employee result = employeeService.updateEmployee(employee, EMPLOYEE_ID);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testUpdateEmployeeName() {
        // Setup
        final Employee employee = new Employee(0L, "name", "dept", "address", "email", 0L);
        final ResponseEntity<Employee> expectedResult = new ResponseEntity<>(new Employee(0L, "name", "dept", "address", "email", 0L), HttpStatus.OK);

        // Configure EmployeeRepo.findById(...).
        final Optional<Employee> employeeOptional = Optional.of(new Employee(0L, "name", "dept", "address", "email", 0L));
        when(employeeRepo.findById(0L)).thenReturn(employeeOptional);

        // Configure EmployeeRepo.save(...).
        final Employee employee1 = new Employee(0L, "name", "dept", "address", "email", 0L);
        when(employeeRepo.save(new Employee(0L, "name", "dept", "address", "email", 0L))).thenReturn(employee1);

        // Run the test
        final ResponseEntity<Employee> result = employeeService.updateEmployeeName(0L, employee);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testDeleteEmployee() {
        // Setup
        // Configure EmployeeRepo.findById(...).
        final Optional<Employee> employeeOptional = Optional.of(new Employee(0L, "name", "dept", "address", "email", 0L));
        when(employeeRepo.findById(0L)).thenReturn(employeeOptional);

        // Run the test
        final Boolean result = employeeService.deleteEmployee(0L);

        // Verify the results
        assertTrue(result);
        verify(employeeRepo).delete(new Employee(0L, "name", "dept", "address", "email", 0L));
    }

    public Employee getEmployee()
    {
        Employee employee = new Employee(EMPLOYEE_ID, EMPLOYEE_NAME, EMPLOYEE_DEPT, EMPLOYEE_ADDRESS, EMPLOYEE_EMAIL, EMPLOYEE_MOBILE);
        return employee;
    }
}
