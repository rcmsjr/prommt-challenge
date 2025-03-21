package com.server.payment.domain;

import com.server.payment.domain.PaymentEntity.Status;
import com.server.payment.domain.exceptions.BusinessRuleException;
import com.server.payment.domain.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class PaymentService implements IPaymentService {

    private final IPaymentRepository paymentRepository;

    @Autowired
    public PaymentService(IPaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentEntity create(PaymentEntity payment) {
        return paymentRepository.save(payment);
    }

    public List<PaymentEntity> fetch() {
        return paymentRepository.findAll();
    }

    public PaymentEntity getById(Integer id) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        return payment.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    public PaymentEntity updateStatusById(Integer id, Status paymentStatus) {
        Optional<PaymentEntity> paymentOptional = paymentRepository.findById(id);
        if (!paymentOptional.isPresent()) {
            throw new ResourceNotFoundException("Payment not found");
        }

        PaymentEntity payment = paymentOptional.get();
        if (payment.getStatus() == Status.PAID) {
            throw new BusinessRuleException("Paid payments cannot be updated");
        }

        payment.setStatus(paymentStatus);
        payment.setPaidAt(paymentStatus == Status.PAID ? new Date() : null);
        return paymentRepository.save(payment);
    }

    public void deleteById(Integer id) {
        Optional<PaymentEntity> paymentOptional = paymentRepository.findById(id);
        if (!paymentOptional.isPresent()) {
            throw new ResourceNotFoundException("Payment not found");
        }

        PaymentEntity payment = paymentOptional.get();
        if (payment.getStatus() == Status.PAID) {
            throw new BusinessRuleException("Paid payments cannot be deleted");
        }

        paymentRepository.deleteById(id);
    }
}
