package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {


    User findUserByEmail(String email);

    void saveUser(User user);

    void updateUserRole(User user, int roleId);

    public List<User> getAllUsers();

    public User getUserById(Integer id);

    void sendPasswordResetEmail(String email);


    boolean isResetTokenValid(User user, String resetToken);

    void updateUserPassword(User user, String newPassword);

   // void invalidateResetToken(User user);

    public void edituser(Integer id);

    void delete(User user);

    public void delete(Integer id);


     public void updateSave(User user);

}