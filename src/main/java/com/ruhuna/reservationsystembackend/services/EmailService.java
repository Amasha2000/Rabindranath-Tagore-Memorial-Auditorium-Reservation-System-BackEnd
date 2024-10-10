package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;
import com.ruhuna.reservationsystembackend.enums.PaymentType;

import java.math.BigDecimal;

public interface EmailService {
     void sendApprovalStatusEmail(String toEmail, String applicantName, ApprovalStatus status);

     void sendPaymentConfirmationEmail(String to, PaymentType paymentType, BigDecimal amount);
}
