package com.server.payment.domain;

import java.util.List;
import java.util.Optional;

public interface IPaymentRepository {
    List<PaymentEntity> findAll();
    Optional<PaymentEntity> findById(Integer id);
    PaymentEntity save(PaymentEntity payment);
    void deleteById(Integer id);
}
