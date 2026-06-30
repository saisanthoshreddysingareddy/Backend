package com.example.scaler.adapters;

import org.springframework.stereotype.Component;

import com.example.scaler.library.sendgrid.Sendgrid;

@Component
public class EmailAdapterImpl implements EmailAdapter {

    private Sendgrid sendgrid = new Sendgrid();

    @Override
    public void sendEmail(String email, String message) throws Exception {
        sendgrid.sendEmailAsync(email, message);
    }
}
