package com.example.scaler.adapters;

import org.springframework.stereotype.Component;

import com.example.scaler.library.sendgrid.Sendgrid;

@Component
public class WhatsappAdapterImpl implements WhatsappAdapter {

    private Sendgrid sendgrid = new Sendgrid();

    @Override
    public void sendWhatsappMessage(String phoneNumber,
                                    String message) throws Exception {

        sendgrid.sendWhatsApp(phoneNumber, message);
    }
}