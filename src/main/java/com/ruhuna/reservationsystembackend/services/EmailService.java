package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import com.ruhuna.reservationsystembackend.enums.PaymentType;
import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;
import java.math.BigDecimal;

public interface EmailService {
     void sendApprovalStatusEmail(String toEmail, String applicantName, ApprovalStatus status);
//     void sendPaymentConfirmationEmail(String to, PaymentType paymentType, BigDecimal amount);
     void sendPaymentReceiptEmail(String to, PaymentType paymentType, BigDecimal amount, byte[] receiptData) throws MessagingException, FileNotFoundException;
     void receiveToVCEmail(String toEmail);
     void newApplicationFormEmail(String toEmail);
     void sendResetPasswordEmail(String email, String resetUrl);
     void sendCancellationRequestEmail(String adminEmail, Reservation reservation);
}
