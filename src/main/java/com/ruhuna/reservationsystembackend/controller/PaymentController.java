package com.ruhuna.reservationsystembackend.controller;

import com.itextpdf.text.DocumentException;
import com.ruhuna.reservationsystembackend.dto.PaymentDto;
import com.ruhuna.reservationsystembackend.entity.Payment;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.services.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/process/{reservationId}")
    public ResponseEntity<PaymentDto> processPayment(
            @PathVariable Long reservationId,
            @RequestBody PaymentDto paymentDto,
            @RequestParam("token") String stripeToken) {
        try {
            PaymentDto createdPayment = paymentService.createPayment(reservationId, paymentDto, stripeToken);
            return ResponseEntity.ok(createdPayment);
        } catch (StripeException | DocumentException | MessagingException | FileNotFoundException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/advance/{reservationId}")
    public ResponseEntity<Boolean> isAdvancePaid(@PathVariable Long reservationId){
        boolean advancePaid = paymentService.isAdvancePaid(reservationId);
        return ResponseEntity.ok(advancePaid);
    }

    @GetMapping("/total/{reservationId}")
    public ResponseEntity<Boolean> isTotalPaid(@PathVariable Long reservationId){
        boolean totalPaid = paymentService.isTotalPaid(reservationId);
        return ResponseEntity.ok(totalPaid);
    }

    @GetMapping("/get/{reservationId}")
    public ResponseEntity<Payment> getPaymentDetailsById(@PathVariable Long reservationId) {
        Payment payment = paymentService.getPaymentDetailsById(reservationId);
        return ResponseEntity.ok(payment);
    }
}
