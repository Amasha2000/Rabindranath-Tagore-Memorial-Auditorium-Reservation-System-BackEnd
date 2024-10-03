package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.enums.ApprovalStatus;

public interface EmailService {
     void sendApprovalStatusEmail(String toEmail, String applicantName, ApprovalStatus status);
}
