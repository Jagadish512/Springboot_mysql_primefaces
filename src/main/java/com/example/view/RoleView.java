package com.example.view;


import com.example.model.Role;
import com.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@ManagedBean(name= "roleView")
@RequestScoped
@Controller
public class RoleView {
    @Autowired
    private RoleService roleService;

    private List<Role> roles;
    private String selectedRole;

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<Role> getRoles() {
        return roleService.getAllRoles();


    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


}
