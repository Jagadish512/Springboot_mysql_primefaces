package com.example.view;

import com.example.model.Employee;
import com.example.model.User;
import com.example.service.EmployeeService;
import com.example.service.UserService;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userView")
@RequestScoped
@Controller
public class UserView {

    @Autowired
    private UserService userService;
    private List<User> users;
    private User user;
    private User editeduser;
    private User selecteduser;
    private User originalUser;

    public void delete(User user) {
        userService.delete(user);
        //refreshList();
    }

    public void edituser(User user) {
        this.originalUser =user;

    }

    public List<User> getUserList() {

        return userService.getAllUsers();
    }

    public User getEditeduser() {

        return editeduser;
    }

    public void setEditeduser(User editeduser) {

        this.editeduser = editeduser;
    }


    public User getSelecteduser() {

        return selecteduser;
    }

    public void setSelecteduser(User selecteduser) {

        this.selecteduser = selecteduser;
    }

    public User getOriginalUser() {
        return originalUser;
    }

    public void setOriginalUser(User originalUser) {
        this.originalUser = originalUser;
    }

    /*public String refreshList() {
        return "/home.xhtml";
    }*/

    public void onRowSelect(SelectEvent event) {
        user = (User) event.getObject();
    }

}
