package com.example.view;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;

@ManagedBean(name="employeeView")
@ViewScoped
@Controller
public class EmployeeView{

	@Autowired
    private EmployeeService employeeService;
    private Employee employee = new Employee();

    private Employee selected;
   // private Employee employee;
    private Employee originalUser;

    public Employee getEmployee() {

        return employee;
    }
    public void delete(Employee employee) {
        employeeService.delete(employee);

        //refreshList();
    }
    public void setEmployee(Employee employee) {

        this.employee = employee;
    }

    public Employee getOriginalUser() {

        return originalUser;
    }

    public void setOriginalUser(Employee originalUser) {

        this.originalUser = originalUser;
    }

    public List<Employee> getEmployeesList() {

        return employeeService.getAllEmployees();
    }

    public void editemployee(Employee employee) {

        this.originalUser =employee;
    }
    public String createEmployee(){

        employeeService.createEmployee(employee);
        return "index?faces-redirect=true";
    }
    public void saveemployee() {

        employeeService.updateemp(originalUser);

    }
    public Employee getSelected() {
        return selected;
    }

    public void setSelected(Employee selected) {

        this.selected = selected;
    }
}