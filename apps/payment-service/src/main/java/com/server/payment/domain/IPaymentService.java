package com.server.payment.domain;

import com.server.payment.domain.PaymentEntity.Status;

import java.util.List;

public interface IPaymentService {
    List<PaymentEntity> fetch();
    PaymentEntity create(PaymentEntity payment);
    PaymentEntity getById(Integer id);
    PaymentEntity updateStatusById(Integer id, Status paymentStatus);
    void deleteById(Integer id);
}
