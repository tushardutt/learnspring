package com.practice.learnspring.repo;

import com.practice.learnspring.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository <Employee,Long>{

}
