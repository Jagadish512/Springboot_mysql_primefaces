package com.example.view;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.model.Employee;
import com.example.service.EmployeeService;

@ManagedBean(name="employeeView")
@RequestScoped
@Controller
public class EmployeeView{
		
	@Autowired
    private EmployeeService employeeService;

    private Employee selected;


    public List<Employee> getEmployeesList() {

        return employeeService.getAllEmployees();
    }

    public void sendDataBack(){
        System.out.println("emp: "+selected);
    }

    public Employee getSelected() {
        return selected;
    }
   /* public String goToPage1() {
        // ...
        return "registration?faces-redirect=true";
    }*/
    public void setSelected(Employee selected) {
        this.selected = selected;
    }
}