package com.ruhuna.reservationsystembackend.services.impl;

import com.itextpdf.text.DocumentException;
import com.ruhuna.reservationsystembackend.dto.PaymentDto;
import com.ruhuna.reservationsystembackend.entity.Admin;
import com.ruhuna.reservationsystembackend.entity.Payment;
import com.ruhuna.reservationsystembackend.entity.Reservation;
import com.ruhuna.reservationsystembackend.enums.PaymentType;
import com.ruhuna.reservationsystembackend.enums.UserRole;
import com.ruhuna.reservationsystembackend.repository.AdminRepository;
import com.ruhuna.reservationsystembackend.repository.PaymentRepository;
import com.ruhuna.reservationsystembackend.repository.ReservationRepository;
import com.ruhuna.reservationsystembackend.services.EmailService;
import com.ruhuna.reservationsystembackend.services.NotificationService;
import com.ruhuna.reservationsystembackend.services.PaymentService;
import com.ruhuna.reservationsystembackend.util.PDFGenerator;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
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

    private final AdminRepository adminRepository;
    private final EmailService emailService;
    private final NotificationService notificationService;


    public PaymentDto createPayment(Long reservationId, PaymentDto paymentDto, String stripeToken) throws StripeException, DocumentException, MessagingException, FileNotFoundException {
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

        byte[] receiptData = PDFGenerator.generatePaymentReceipt(reservation,String.valueOf(paymentDto.getPaymentType()), paymentDto.getAmount());

        //send email
        emailService.sendPaymentReceiptEmail(reservation.getUser().getEmail(), payment.getPaymentType() , paymentDto.getAmount(), receiptData);

        Admin admin = adminRepository.findByUserRole(UserRole.ROLE_ADMIN);
        emailService.sendPaymentReceiptEmail(admin.getEmail(), payment.getPaymentType() , paymentDto.getAmount(), receiptData);

        emailService.sendPaymentReceiptEmail("amasham478@gmail.com", payment.getPaymentType() , paymentDto.getAmount(), receiptData);

        String redirectUrlAdmin = "/manage-reservations/";

        //send notifications
        if(paymentDto.getPaymentType() == PaymentType.ADVANCE_FEE){
            String redirectUrl = "/payment/";
            notificationService.createNotification("Your payment of Rs."+paymentDto.getAmount()+" for advance fee was successful and make the total payment for reserve the auditorium.",
                    reservation.getUser().getUserId(),
                    redirectUrl);

            notificationService.createAdminNotification("Payment of Rs." + paymentDto.getAmount() + " for advance fee of Reservation ID: "+ reservation.getReservationId() +" was successful.Click here to view",
                    admin.getAdminId(),
                    redirectUrlAdmin);
        }else {
            String redirectUrl = "/payment/";
            notificationService.createNotification("Your payment of Rs." + paymentDto.getAmount() + " for total fee was successful and reservation has completed.",
                    reservation.getUser().getUserId(),
                    redirectUrl);

            notificationService.createAdminNotification("Payment of Rs." + paymentDto.getAmount() + " for total fee of Reservation ID: "+ reservation.getReservationId() +" was successful.Click here to view",
                    admin.getAdminId(),
                    redirectUrlAdmin);
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
