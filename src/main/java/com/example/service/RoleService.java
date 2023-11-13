package com.example.service;


import com.example.model.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface RoleService {

    List<Role> getAllRoles();
}
