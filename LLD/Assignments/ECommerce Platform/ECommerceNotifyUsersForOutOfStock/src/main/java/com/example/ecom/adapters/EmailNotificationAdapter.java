package com.example.ecom.adapters;

import org.springframework.stereotype.Component;

import com.example.ecom.libraries.Sendgrid;
import lombok.AllArgsConstructor;

@Component
public class EmailNotificationAdapter implements NotificationAdapter{
    Sendgrid sendgrid = new Sendgrid();

    public void sendNotification(String email, String subject, String body){
        // Call email notification
        sendgrid.sendEmailAsync(email,subject,body);
    }

}
