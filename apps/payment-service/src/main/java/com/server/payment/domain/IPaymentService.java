package com.server.payment.domain;

import com.server.payment.domain.Payment.Status;

import java.util.List;

public interface IPaymentService {
    List<Payment> fetch();
    Payment create(Payment payment);
    Payment getById(Long id);
    Payment updateStatusById(Long id, Status paymentStatus);
    void deleteById(Long id);
}
