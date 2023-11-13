package com.example.controller;

import com.example.model.Employee;
import com.example.model.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class RolesController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(path="/role", method= RequestMethod.GET)
    public List<Role> getAllRoles(){

        return roleService.getAllRoles();
    }
}
