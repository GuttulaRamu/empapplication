package com.imaginnovate.empapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@Pattern(regexp = "E\\d+", message = "Employee ID must start with 'E' followed by digits")
	private String employeeId;

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	@Column(unique = true)
	private String email;

	@ElementCollection
	@NotEmpty(message = "At least one phone number is required")
	private List<@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String> phoneNumbers;

	@NotNull(message = "Date of joining is mandatory")
	private LocalDate doj;

	@NotNull(message = "Salary is mandatory")
	@Positive(message = "Salary must be a positive number")
	private Double salary;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public LocalDate getDoj() {
		return doj;
	}

	public void setDoj(LocalDate doj) {
		this.doj = doj;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Employee(
			@NotBlank(message = "First name is mandatory") String firstName,
			@NotBlank(message = "Last name is mandatory") String lastName,
			@Email(message = "Email should be valid") @NotBlank(message = "Email is mandatory") String email,
			@NotEmpty(message = "At least one phone number is required") List<@Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits") String> phoneNumbers,
			@NotNull(message = "Date of joining is mandatory") LocalDate doj,
			@NotNull(message = "Salary is mandatory") @Positive(message = "Salary must be a positive number") Double salary) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumbers = phoneNumbers;
		this.doj = doj;
		this.salary = salary;
	}

	public Employee() {
		super();
	}
	
	


}
