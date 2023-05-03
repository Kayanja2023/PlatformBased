package com.example.apparchitects;

import android.os.AsyncTask;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioHelper {

    private static final String ACCOUNT_SID = "ACf35c7233b4878f3606e5b33e06f96e65";
    private static final String AUTH_TOKEN = "56b601ff94530b42b9f649af2140045e";
    private static final String FROM_PHONE_NUMBER = "+15075015263";

    public static void sendSMS(String toPhoneNumber, String messageBody) {
        new SendSMSTask().execute(toPhoneNumber, messageBody);
    }

    private static class SendSMSTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String toPhoneNumber = params[0];
            String messageBody = params[1];

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                            new PhoneNumber(toPhoneNumber),
                            new PhoneNumber(FROM_PHONE_NUMBER),
                            messageBody)
                    .create();

            System.out.println("SMS sent: " + message.getSid());
            return null;
        }
    }
}
