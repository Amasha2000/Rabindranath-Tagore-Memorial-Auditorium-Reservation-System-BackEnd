package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.PaymentDto;
import com.stripe.exception.StripeException;

public interface PaymentService {
    PaymentDto createPayment(Long reservationId, PaymentDto paymentDto, String stripeToken) throws StripeException;

    boolean isAdvancePaid(Long reservationId);

    boolean isTotalPaid(Long reservationId);
}
