package com.server.payment.infrastructure.database.memory;

import com.server.payment.domain.PaymentEntity;
import com.server.payment.domain.IPaymentRepository;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PaymentRepository implements IPaymentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PaymentEntity> findAll() {
        List<PaymentModel> payments = entityManager.createQuery("SELECT p FROM PaymentModel p", PaymentModel.class)
            .getResultList();
        if (payments.isEmpty()) {
            return new ArrayList<>();
        }

        List<PaymentEntity> paymentEntities = new ArrayList<>();
        for (PaymentModel payment : payments) {
            paymentEntities.add(payment.toDomainEntity());
        }
        return paymentEntities;
    }

    @Override
    public Optional<PaymentEntity> findById(Integer id) {
        PaymentModel payment = entityManager.find(PaymentModel.class, id);
        return Optional.ofNullable(payment != null ? payment.toDomainEntity() : null);
    }

    @Override
    @Transactional
    public PaymentEntity save(PaymentEntity payment) {
        PaymentModel paymentModel = new PaymentModel();
        paymentModel.setPayerEmail(payment.getPayerEmail());
        paymentModel.setCurrency(payment.getCurrency());
        paymentModel.setAmount(payment.getAmount());
        paymentModel.setStatus(payment.getStatus());
        paymentModel.setCreatedDate(payment.getCreatedAt());
        paymentModel.setPaidDate(payment.getPaidAt());
        if (payment.getId() == null) {
            entityManager.persist(paymentModel);
        } else {
            paymentModel.setId(payment.getId());
            entityManager.merge(paymentModel);
        }
        return paymentModel.toDomainEntity();
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        PaymentModel payment = entityManager.find(PaymentModel.class, id);
        if (payment != null) {
            entityManager.remove(payment);
        }
    }
}
