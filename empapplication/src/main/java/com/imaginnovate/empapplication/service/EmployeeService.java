package com.imaginnovate.empapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.empapplication.dto.TaxDetails;
import com.imaginnovate.empapplication.entity.Employee;
import com.imaginnovate.empapplication.exception.EmployeeNotFoundException;
import com.imaginnovate.empapplication.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EmployeeService {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee saveEmployee(Employee employee) {
    	
        String newEmployeeId = generateNewEmployeeId();
        employee.setEmployeeId(newEmployeeId);  // Set the generated ID
        return employeeRepository.save(employee);
    }
    private String generateNewEmployeeId() {

    	String latestEmployeeId = employeeRepository.findLatestEmployeeId();
    	if (latestEmployeeId == null) {
    		return "E100";  // Start with the first ID
    	}

    	// Extract the numeric part of the employee ID, increment it, and format back to E00X
    	int id = Integer.parseInt(latestEmployeeId.substring(1)) + 1;
    	return String.format("E%03d", id);  // Generates IDs like E102, E103, etc.
    }
    

    public Optional<Employee> findEmployeeById(String employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public double calculateYearlySalary(Employee employee) {
        LocalDate current = LocalDate.now();
        LocalDate start = employee.getDoj();

        if (start.getYear() == current.getYear()) {
            int monthsWorked = (int) ChronoUnit.MONTHS.between(start, current) + 1;
            return employee.getSalary() * monthsWorked;
        } else {
            return employee.getSalary() * 12;
        }
    }

    public double calculateTax(double salary) {
        double tax = 0;

        if (salary <= 250000) {
            return tax;
        } else if (salary <= 500000) {
            tax += (salary - 250000) * 0.05;
        } else if (salary <= 1000000) {
            tax += (500000 - 250000) * 0.05;
            tax += (salary - 500000) * 0.10;
        } else {
            tax += (500000 - 250000) * 0.05;
            tax += (1000000 - 500000) * 0.10;
            tax += (salary - 1000000) * 0.20;
        }

        return tax;
    }

    public double calculateCess(double salary) {
        if (salary > 2500000) {
            return (salary - 2500000) * 0.02;
        }
        return 0;
    }

    public TaxDetails calculateTaxDeductions(Employee employee) {
        double yearlySalary = calculateYearlySalary(employee);
        double taxAmount = calculateTax(yearlySalary);
        double cessAmount = calculateCess(yearlySalary);
        return new TaxDetails(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(), yearlySalary, taxAmount, cessAmount);

    }
    
}
