package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface UserService {


    User findUserByEmail(String email);

    void saveUser(User user);

    public List<User> getAllUsers();

    public User getUserById(Integer id);



    public void edituser(Integer id);

    void delete(User user);

    public void delete(Integer id);


}