package com.example.service;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;


@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final static Logger logger = LoggerFactory
            .getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateSave(User user) {

        userRepository.save(user);
    }

    @Override
    public void sendPasswordResetEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            String resetToken = generateUniqueToken();
            System.out.println("Generated Token: " + resetToken);
            user.setPassword(resetToken);
           // userRepository.save(user);

            sendEmail(user.getEmail(), resetToken, user.getId());
        } else {
            throw new IllegalArgumentException("User with email " + email + " not found.");
        }
    }

    @Override
    public void updateUserPassword(User user, String newPassword) {

        // Check if the user is not null
        if (user != null) {
            // Update the user's password
            user.setPassword(encodePassword(newPassword)); // Assuming you have a method to encode passwords

            // Save the updated user entity
         //   userRepository.save(user);
        }
    }

    private String encodePassword(String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(newPassword);
    }

    @Override
    public boolean isResetTokenValid(User user, String resetToken) {
        // Check if the user and resetToken are not null
        if (user == null || resetToken == null) {
            return false;
        }

        // Check if the provided reset token matches the stored reset token
       /* if (!resetToken.equals(user.getResetToken())) {
            return false;
        }
*/
        // Check if the reset token has expired
       /* if (isResetTokenExpired(user)) {
            return false;
        }*/

        // If all checks pass, the reset token is considered valid
        return true;
    }

    private String generateUniqueToken() {
        // Implement your logic to generate a unique token (e.g., using UUID)
        return UUID.randomUUID().toString();
    }

    private void sendEmail(String email, String resetToken, Integer id) {

        String host = "smtp.gmail.com";
        String port = "587";
        String username = "jagadishchintanippu@gmail.com";
        String password = "ixjx hmma olib afuu";

        // Set up properties for the mail session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Create a session with the provided properties
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset");

            String emailContent = "Click the following link to reset your password: "
                    + "http://localhost:8080/reset-password.faces?userId=" + id + "&token=" + resetToken;
            ;
           // http://localhost:8080/reset-password.faces?userId=" + id;
            //http://localhost:8080/reset-password.faces?token=" + resetToken + "&userId=" + id;
            message.setText(emailContent);

            Transport.send(message);

            System.out.println("Password reset email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(Integer id) {

        return userRepository.findOne(id);
    }

    @Override
    public void edituser(Integer id) {

        User user = userRepository.findOne(id);
    }


    @Override
    public void delete(User user) {

        userRepository.delete(user);
    }

    @Override
    public void delete(Integer id) {
        User user = userRepository.findOne(id);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void updateUserRole(User user, int roleId) {

        User originalUser = userRepository.findById(user.getId());

        Role role = roleRepository.findById(roleId);
        originalUser.setRole(role);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        Set<Role> roles = user != null ? user.getRoles() : new TreeSet<Role>();
        List<GrantedAuthority> authorities = getUserAuthority(roles);
        return buildUserForAuthentication(user, authorities, userName);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities, String userName) {
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
        } else {
            logger.info("User not found: " + userName);
            throw new UsernameNotFoundException("User not found: " + userName);
        }
    }
}