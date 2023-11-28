package com.example.view;

import com.example.service.MobileNotificationService;
import com.example.service.MobileNotificationServiceImpl;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="mobileNotificationView")
@RequestScoped
@Controller
public class MobileNotificationView {
    private String message;
    private String phoneNumber;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Autowired
    private MobileNotificationService mobileNotificationService;

    @Autowired
    public MobileNotificationView(MobileNotificationService mobileNotificationService) {
        this.mobileNotificationService = mobileNotificationService;
    }
    public void sendMessage() {

        boolean success=  mobileNotificationService.sendMobileMessage(message, phoneNumber);

        FacesMessage facesMessage;
        if (success) {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message sent successfully!", null);
        } else {
            facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to send message.", null);
        }

        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
