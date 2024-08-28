package com.imaginnovate.empapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.imaginnovate.empapplication.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
    // Custom query to find the latest employee ID
    @Query("SELECT e.employeeId FROM Employee e ORDER BY e.employeeId DESC LIMIT 1")
    String findLatestEmployeeId();

}
