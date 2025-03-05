package com.server.payment.domain;

import com.server.payment.domain.PaymentEntity.Status;
import com.server.payment.domain.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public PaymentEntity getById(Integer id) throws ResourceNotFoundException {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        return payment.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    public PaymentEntity updateStatusById(Integer id, Status paymentStatus) throws ResourceNotFoundException {
        Optional<PaymentEntity> paymentOptional = paymentRepository.findById(id);
        if (!paymentOptional.isPresent()) {
            throw new ResourceNotFoundException("Payment not found");
        }

        PaymentEntity payment = paymentOptional.get();
        payment.setStatus(paymentStatus);
        return paymentRepository.save(payment);
    }

    public void deleteById(Integer id) {
        paymentRepository.deleteById(id);
    }
}
