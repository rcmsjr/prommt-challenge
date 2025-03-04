package com.server.payment.domain;

import com.server.payment.domain.PaymentEntity.Status;

import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

@Service
public class PaymentService implements IPaymentService {

    public PaymentEntity create(PaymentEntity payment) {
        Random random = new Random();
        Long paymentId = random.nextLong(1000000); // Generates a random payment ID between 0 and 999999
        payment.setId(paymentId);
        return payment;
    }

    public List<PaymentEntity> fetch() {
        // Placeholder for implementation to retrieve all payments
        List<PaymentEntity> payments = new ArrayList<>();
        Random random = new Random();
        int randomNumber = 3 + random.nextInt(7); // Generates a random number between 3 and 9
        for (int i = 0; i < randomNumber; i++) {
            PaymentEntity payment = new PaymentEntity();
            payment.setId(random.nextLong(1000000)); // Generates a random payment ID between 0 and 999999
            payment.setPayerEmail(payment.getId() + "@email.com");
            payment.setStatus(i % 2 == 0 ? PaymentEntity.Status.PAID : PaymentEntity.Status.PENDING);
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

    public PaymentEntity getById(Long id) {
        // Placeholder for implementation to retrieve a payment by its ID
        PaymentEntity payment = new PaymentEntity();
        payment.setId(id);
        payment.setPayerEmail("rcmsjr@gmail.com");
        payment.setStatus(id % 2 == 0 ? PaymentEntity.Status.PAID : PaymentEntity.Status.PENDING);
        payment.setCurrency("EUR");
        payment.setAmount(100);
        payment.setCreatedAt(new java.util.Date());
        if (id % 2 == 0) {
            payment.setPaidAt(new java.util.Date());
        }
        return payment;
    }

    public PaymentEntity updateStatusById(Long id, Status paymentStatust) {
        // Placeholder for implementation to update a payment
        PaymentEntity payment = new PaymentEntity();
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
        System.out.println("PaymentEntity with ID " + id + " has been deleted.");
    }
}
