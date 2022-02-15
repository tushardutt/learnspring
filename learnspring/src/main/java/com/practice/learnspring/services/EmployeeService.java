package com.practice.learnspring.services;

import com.practice.learnspring.entities.Employee;
import com.practice.learnspring.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Transactional
    public List<Employee> getEmployees(){

        return employeeRepo.findAll();
    }
    @Transactional
    public Employee getEmployee(Long id){

        return employeeRepo.findById(id).get();
    }

    @Transactional
    public Employee addEmployee(Employee employee) {

        return employeeRepo.save(employee);
    }

    public Employee updateEmployee(Employee employee, Long id) {
        return employeeRepo.findById(id)
                .map(newEmployee -> {
                    //newEmployee.setId(employee.getId());
                    newEmployee.setName(employee.getName());
                    newEmployee.setDept(employee.getDept());
                    newEmployee.setAddress(employee.getAddress());
                    newEmployee.setEmail(employee.getEmail());
                    newEmployee.setMobile(employee.getMobile());
                    return employeeRepo.save(newEmployee);
                })
                .orElseGet(() -> {
                    return employeeRepo.save(employee);
                });
    }

    public Boolean deleteEmployee(Long id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found of :: "+ id));
        employeeRepo.delete(employee);
        return true;
    }

    public ResponseEntity<Employee> updateEmployeeName(Long id, Employee employee) {

        Employee employee1 = employeeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: "+ id));

        employee1.setName(employee.getName());
        final Employee updatedEmployee = employeeRepo.save(employee1);
        return ResponseEntity.ok(updatedEmployee);

    }
}
