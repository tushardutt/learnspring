package com.practice.learnspring.controllers;

import com.practice.learnspring.entities.Employee;
import com.practice.learnspring.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class EmployeeController{
    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    private EmployeeService employeeService;
@GetMapping("/employees")
public List<Employee> getEmployees()
{

     return employeeService.getEmployees();
}
@GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable Long id){

    return employeeService.getEmployee(id);
}
@PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee)
{
    logger.info("Request to create employee: {}", employee);
    com.practice.learnspring.entities.Employee entity = employeeService.addEmployee(employee);
    return new ResponseEntity<>(entity, HttpStatus.CREATED);
}

@PutMapping("/employees/{id}")
public Employee updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
    return employeeService.updateEmployee(employee, id);
 }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        logger.debug("Request to delete employee:");
    employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Data deleted successfully", HttpStatus.OK);
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeName(@PathVariable Long id, @RequestBody Employee employee) {
        ResponseEntity<Employee> entity = employeeService.updateEmployeeName(id,employee);
        return new ResponseEntity<Employee>(HttpStatus.OK);
    }

}