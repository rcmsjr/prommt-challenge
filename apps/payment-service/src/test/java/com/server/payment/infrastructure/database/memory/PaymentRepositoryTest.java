package com.server.payment.infrastructure.database.memory;

import com.server.payment.domain.PaymentEntity;
import com.server.payment.domain.PaymentEntity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class PaymentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentRepository paymentRepository;

    private PaymentModel paymentModel;

    @BeforeEach
    public void setUp() {
        paymentModel = new PaymentModel();
        paymentModel.setPayerEmail("test@example.com");
        paymentModel.setCurrency("USD");
        paymentModel.setAmount(10000);
        paymentModel.setStatus(Status.PENDING);
        paymentModel.setCreatedDate(new Date());
        paymentModel.setPaidDate(null);
        entityManager.persist(paymentModel);
        entityManager.flush();
    }

    @Test
    public void testFindAll() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        assertFalse(payments.isEmpty());
        assertEquals(1, payments.size());
    }

    @Test
    public void testFindById() {
        Optional<PaymentEntity> payment = paymentRepository.findById(paymentModel.getId());
        assertTrue(payment.isPresent());
        assertEquals(paymentModel.getPayerEmail(), payment.get().getPayerEmail());
    }

    @Test
    public void testSave() {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPayerEmail("new@example.com");
        paymentEntity.setCurrency("EUR");
        paymentEntity.setAmount(20000);
        paymentEntity.setStatus(Status.PAID);
        paymentEntity.setCreatedAt(new Date());
        paymentEntity.setPaidAt(new Date());

        PaymentEntity savedPayment = paymentRepository.save(paymentEntity);
        assertNotNull(savedPayment.getId());
        assertEquals("new@example.com", savedPayment.getPayerEmail());
    }

    @Test
    public void testDeleteById() {
        paymentRepository.deleteById(paymentModel.getId());
        Optional<PaymentEntity> payment = paymentRepository.findById(paymentModel.getId());
        assertFalse(payment.isPresent());
    }
}
