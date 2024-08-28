package com.imaginnovate.empapplication.cotroller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.imaginnovate.empapplication.dto.TaxDetails;
import com.imaginnovate.empapplication.entity.Employee;
import com.imaginnovate.empapplication.service.EmployeeService;


@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
    private EmployeeService employeeService;

    @PostMapping("employees")
    public ResponseEntity<?> createEmployee(@Validated @RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.saveEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/employees/{employeeId}/tax-deductions")
    public ResponseEntity<?> getTaxDeductions(@PathVariable String employeeId) {
        try {
        	
            Optional<Employee> employeeOptional = employeeService.findEmployeeById(employeeId);

            if (!employeeOptional.isPresent()) {
                return ResponseEntity.status(404).body("Employee not found");
            }

            Employee employee = employeeOptional.get();
//            Employee taxDeductions = employeeService.calculateTaxDeductions(employee);
//            return ResponseEntity.ok(taxDeductions);

            
            TaxDetails taxDetails = employeeService.calculateTaxDeductions(employee);
            return new ResponseEntity<>(taxDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
}
