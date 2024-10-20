package com.ruhuna.reservationsystembackend.services.impl;

import com.ruhuna.reservationsystembackend.dto.PaymentDto;
import com.ruhuna.reservationsystembackend.entity.Payment;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.PaymentType;
import com.ruhuna.reservationsystembackend.repository.PaymentRepository;
import com.ruhuna.reservationsystembackend.repository.ReservationRepository;
import com.ruhuna.reservationsystembackend.services.EmailService;
import com.ruhuna.reservationsystembackend.services.NotificationService;
import com.ruhuna.reservationsystembackend.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    private final EmailService emailService;
    private final NotificationService notificationService;


    public PaymentDto createPayment(Long reservationId, PaymentDto paymentDto, String stripeToken) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        // Get reservation by id
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        // Charge the user via Stripe
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentDto.getAmount().multiply(new BigDecimal(100)).intValue()); // Stripe expects amount in cents
        chargeParams.put("currency", "usd");
        chargeParams.put("source", stripeToken); // Stripe token from frontend
        chargeParams.put("description", "Payment for reservation ID: " + reservationId);

        Charge.create(chargeParams);

        // After successful payment, save to the database
        Payment payment = Payment.builder()
                .paymentDate(LocalDate.now())
                .paymentTime(LocalTime.now())
                .paymentType(paymentDto.getPaymentType())
                .amount(paymentDto.getAmount())
                .reservation(reservation)
                .build();

        paymentRepository.save(payment);

        //send email
        emailService.sendPaymentConfirmationEmail(reservation.getUser().getEmail(), payment.getPaymentType() , paymentDto.getAmount());

        //send notifications
        if(paymentDto.getPaymentType() == PaymentType.ADVANCE_FEE){
            String redirectUrl = "/payment/";
            notificationService.createNotification("Your payment of Rs."+paymentDto.getAmount()+" for advance fee was successful and make the total payment for reserve the auditorium.",
                    reservationId,
                    redirectUrl);
        }else {
            String redirectUrl = "/payment/";
            notificationService.createNotification("Your payment of Rs." + paymentDto.getAmount() + " for total fee was successful and reservation has completed.",
                    reservationId,
                    redirectUrl);
        }

        // Return payment data to the frontend
        return PaymentDto.builder()
                .id(payment.getPaymentId())
                .paymentDate(payment.getPaymentDate())
                .paymentTime(payment.getPaymentTime())
                .paymentType(payment.getPaymentType())
                .amount(payment.getAmount())
                .build();
    }

    @Override
    public boolean isAdvancePaid(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        return paymentRepository.existsPaymentByReservationAndPaymentType(reservation,PaymentType.ADVANCE_FEE);
    }

    @Override
    public boolean isTotalPaid(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        return paymentRepository.existsPaymentByReservationAndPaymentType(reservation,PaymentType.TOTAL_FEE);
    }

    @Override
    public Payment getPaymentDetailsById(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        return paymentRepository.findByReservation(reservation);
    }
}
