package com.example.service;

import com.example.model.TwilioCredentials;
import com.example.repository.TwilioCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioCredentialsService {
    private final TwilioCredentialsRepository twilioCredentialsRepository;

    @Autowired
    public TwilioCredentialsService(TwilioCredentialsRepository twilioCredentialsRepository) {
        this.twilioCredentialsRepository = twilioCredentialsRepository;
    }


    public TwilioCredentials getTwilioCredentials() {
        // For simplicity, this example assumes there is only one row in the table

        System.out.println("Fetching Twilio credentials from the database...");
        return twilioCredentialsRepository.findOne(1L);
    }
}
