package com.example.controller;

import com.example.model.Employee;
import com.example.model.User;
import com.example.service.EmployeeService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String delete(@RequestParam Integer id) {
        userService.delete(id);
        return "redirect:/home"; // Redirect to a product listing page.
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
    public String edituser(@RequestParam Integer id) {
        userService.delete(id);
        return ""; // Redirect to a product listing page.
    }
}
