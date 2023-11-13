package com.example.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.example.model.Role;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findByActive(true);
        //return employeeRepository.findAll();
    }

    @Override
    public void delete(Employee employee) {

        employeeRepository.delete(employee);
    }

    @Override
    public void delete(long id) {
        Employee employee = employeeRepository.findOne(id);
        if (employee != null) {
            employeeRepository.delete(employee);
        }
    }

    @Override
    public Employee getEmployeeById(long id) {

        return employeeRepository.findOne(id);
    }

    @Override
    public void updateemp(Employee employee) {

        employeeRepository.save(employee);
    }

    @Override
    public void createEmployee(Employee employee) {
        employee.setActive(true);
        employeeRepository.save(employee);
    }

}
