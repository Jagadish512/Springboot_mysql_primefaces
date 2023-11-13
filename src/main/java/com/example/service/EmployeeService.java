package com.example.service;

import com.example.model.Employee;
import com.example.model.User;

import java.util.List;

public interface EmployeeService {
	
	List<Employee> getAllEmployees();

	void delete(long id);

	Employee getEmployeeById(long id);

	public void updateemp(Employee employee);

	void createEmployee(Employee employee);

	public void delete(Employee employee);
}
