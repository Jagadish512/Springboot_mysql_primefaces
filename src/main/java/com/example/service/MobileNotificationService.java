package com.example.service;


public interface MobileNotificationService {

    boolean sendMobileMessage(String message, String phoneNumber);
}
