package com.example.view;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ManagedBean(name = "resetPasswordView")
@RequestScoped
@Controller
public class ResetPasswordView {

    @Autowired
    private UserService userService;

    private String newPassword;
    private String confirmPassword;

    public String getNewPassword() {

        return newPassword;
    }

    public void setNewPassword(String newPassword) {

        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {

        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {

        this.confirmPassword = confirmPassword;
    }

    public void resetPassword() {

        if (!newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", null));
            return;
        }

        //int userId = 38;
       // String resetToken = "35dd5f03-3f83-4238-b1af-a622d1be6207";

       Long userId = getUserIdFromURL();
       String resetToken = getResetTokenFromURL();
       //    System.out.println(request.getParameter("UserID"));

        if (resetToken != null) {
            User user = userService.getUserById(Math.toIntExact(userId));

                if (user != null) {
                userService.updateUserPassword(user, newPassword);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Password reset successful", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid or expired reset token", null));
            }
        }
    }

    private Long getUserIdFromURL() {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext externalContext = fc.getExternalContext();

        // Get the request parameter map
        Map<String, String> requestParameters = externalContext.getRequestParameterMap();

        // Print the complete query string
        System.out.println("Query String: " + externalContext.getRequestParameterMap());

        // Retrieve the "userId" parameter from the map
        String userIdParam = requestParameters.get("userId");

        // Print the extracted parameter value
        System.out.println("User ID Parameter: " + userIdParam);

        // Check if the parameter is not null or empty before parsing
        if (userIdParam != null && !userIdParam.isEmpty()) {
            try {
                // Parse the parameter value to Long
                Long userId = Long.parseLong(userIdParam);
                return userId;
            } catch (NumberFormatException e) {
                // Handle the case where the parameter is not a valid Long
                e.printStackTrace(); // Log or handle the exception as needed
            }
        }
        return null;

    }

    private String getResetTokenFromURL() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParameters = facesContext.getExternalContext().getRequestParameterMap();

        return requestParameters.get("token");

    }
}
