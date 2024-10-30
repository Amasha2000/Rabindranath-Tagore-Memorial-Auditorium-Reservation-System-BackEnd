package com.ruhuna.reservationsystembackend.services;

import com.itextpdf.text.DocumentException;
import com.ruhuna.reservationsystembackend.dto.PaymentDto;
import com.ruhuna.reservationsystembackend.entity.Payment;
import com.stripe.exception.StripeException;
import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;

public interface PaymentService {
    PaymentDto createPayment(Long reservationId, PaymentDto paymentDto, String stripeToken) throws StripeException, DocumentException, MessagingException, FileNotFoundException;

    boolean isAdvancePaid(Long reservationId);

    boolean isTotalPaid(Long reservationId);
    Payment getPaymentDetailsById(Long reservationId);
}
