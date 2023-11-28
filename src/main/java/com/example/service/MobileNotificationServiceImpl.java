package com.example.service;

import com.example.model.TwilioCredentials;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class MobileNotificationServiceImpl implements MobileNotificationService{

   /* private static final String ACCOUNT_SID = "AC0986238abd57ffcf15de5ecf15dd39cc";
    private static final String AUTH_TOKEN = "98c17de1b25f818150c63077d10e5d33";
    private static final String TWILIO_PHONE_NUMBER = "+12512835367";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }*/

    private final TwilioCredentialsService twilioCredentialsService;

    public MobileNotificationServiceImpl(TwilioCredentialsService twilioCredentialsService) {
        this.twilioCredentialsService = twilioCredentialsService;
    }

    @Override
    public boolean sendMobileMessage(String message, String phoneNumber) {

        TwilioCredentials twilioCredentials = twilioCredentialsService.getTwilioCredentials();

        if (twilioCredentials != null) {
            try {
                Twilio.init(twilioCredentials.getAccountSid(), twilioCredentials.getAuthToken());
                Message twilioMessage = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(twilioCredentials.getTwilioPhoneNumber()),
                        message
                ).create();
                // System.out.println("Twilio API Response: " + twilioMessage.getSid() + " - " + twilioMessage.getStatus());

                return twilioMessage.getStatus() == Message.Status.SENT;
            } catch (TwilioException e) {
                e.printStackTrace();

            }
        }
            return false;

    }
}
