package com.server.payment.domain;

import com.server.payment.domain.Payment.Status;

import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentService implements IPaymentService {

    public Payment create(Payment payment) {
        Random random = new Random();
        Long paymentId = random.nextLong(1000000); // Generates a random payment ID between 0 and 999999
        payment.setId(paymentId);
        return payment;
    }

    public List<Payment> fetch() {
        // Placeholder for implementation to retrieve all payments
        List<Payment> payments = new ArrayList<>();
        Random random = new Random();
        int randomNumber = 3 + random.nextInt(7); // Generates a random number between 3 and 9
        for (int i = 0; i < randomNumber; i++) {
            Payment payment = new Payment();
            payment.setId(random.nextLong(1000000)); // Generates a random payment ID between 0 and 999999
            payment.setPayerEmail(payment.getId() + "@email.com");
            payment.setStatus(i % 2 == 0 ? Payment.Status.PAID : Payment.Status.PENDING);
            payment.setCurrency("EUR");
            payment.setAmount(i * 100);
            payment.setCreatedAt(new java.util.Date());
            if (i % 2 == 0) {
                payment.setPaidAt(new java.util.Date());
            }
            payments.add(payment);
        }
        return payments;
    }

    public Payment getById(Long id) {
        // Placeholder for implementation to retrieve a payment by its ID
        Payment payment = new Payment();
        payment.setId(id);
        payment.setPayerEmail("rcmsjr@gmail.com");
        payment.setStatus(id % 2 == 0 ? Payment.Status.PAID : Payment.Status.PENDING);
        payment.setCurrency("EUR");
        payment.setAmount(100);
        payment.setCreatedAt(new java.util.Date());
        if (id % 2 == 0) {
            payment.setPaidAt(new java.util.Date());
        }
        return payment;
    }

    public Payment updateStatusById(Long id, Status paymentStatust) {
        // Placeholder for implementation to update a payment
        Payment payment = new Payment();
        payment.setId(id);
        payment.setPayerEmail("rcmsjr@gmail.com");
        payment.setStatus(paymentStatust);
        payment.setCurrency("EUR");
        payment.setAmount(100);
        payment.setCreatedAt(new java.util.Date());
        return payment;
    }

    public void deleteById(Long id) {
        // Placeholder for implementation to delete a payment by its ID
        System.out.println("Payment with ID " + id + " has been deleted.");
    }
}
