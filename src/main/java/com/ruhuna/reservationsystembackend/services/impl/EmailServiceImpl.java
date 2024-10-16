package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import com.ruhuna.reservationsystembackend.enums.PaymentType;
import com.ruhuna.reservationsystembackend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    //approval status mail
    @Override
    public void sendApprovalStatusEmail(String toEmail, String applicantName, ApprovalStatus status) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject("Auditorium Reservation Approval Status Updated");
        message.setText("Dear " + applicantName + ",\n\n" +
                "Your reservation's approval status has been updated to: " + status + ".\n" +
                "Please log in to view more details: " +
                "http://localhost:3000/user-login" + "\n\n" +  // Replace with actual login page URL
                "Thank you for using our service.\n\n" +
                "Best regards,\nUniversity of Ruhuna Auditorium Management");

        mailSender.send(message);
    }

    @Override
    public void sendPaymentConfirmationEmail(String to, PaymentType paymentType, BigDecimal amount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Payment Confirmation for Reservation " + paymentType);
        message.setText("Dear applicant,\n\n" +
                "Your payment of Rs." + amount + " for " + paymentType + " has been successfully processed.\n" +
                "Please log in to view more details: " +
                "http://localhost:3000/user-login" + "\n\n" +  // Replace with actual login page URL
                "Thank you for using our service.\n\n" +
                "Best regards,\nUniversity of Ruhuna Auditorium Management");

        mailSender.send(message);
    }
}
