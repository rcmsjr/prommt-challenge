package com.server.payment.domain;

import com.server.payment.domain.PaymentEntity.Status;
import com.server.payment.domain.exceptions.BusinessRuleException;
import com.server.payment.domain.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceTest {

    @Mock
    private IPaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePayment() {
        PaymentEntity payment = new PaymentEntity();
        PaymentEntity returnPayment = payment.clone();
        returnPayment.setCreatedAt(new Date());
        when(paymentRepository.save(payment)).thenReturn(returnPayment);

        PaymentEntity createdPayment = paymentService.create(payment);
        assertEquals(returnPayment, createdPayment);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testFetchPayments() {
        PaymentEntity payment1 = new PaymentEntity();
        PaymentEntity payment2 = new PaymentEntity();
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment1, payment2));

        List<PaymentEntity> payments = paymentService.fetch();
        assertEquals(2, payments.size());
        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    public void testGetPaymentById() {
        PaymentEntity payment = new PaymentEntity();
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        PaymentEntity foundPayment = paymentService.getById(1);
        assertEquals(payment, foundPayment);
        verify(paymentRepository, times(1)).findById(1);
    }

    @Test
    public void testGetPaymentById_NotFound() {
        when(paymentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> paymentService.getById(1));
        verify(paymentRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdatePaymentStatus() {
        PaymentEntity payment = new PaymentEntity();
        payment.setStatus(Status.PENDING);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));
        when(paymentRepository.save(payment)).thenReturn(payment);

        PaymentEntity updatedPayment = paymentService.updateStatusById(1, Status.PAID);
        assertEquals(Status.PAID, updatedPayment.getStatus());
        assertNotNull(updatedPayment.getPaidAt());
        verify(paymentRepository, times(1)).findById(1);
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    public void testUpdatePaymentStatus_PaidPayment() {
        PaymentEntity payment = new PaymentEntity();
        payment.setStatus(Status.PAID);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        assertThrows(BusinessRuleException.class, () -> paymentService.updateStatusById(1, Status.FAILED));
        verify(paymentRepository, times(1)).findById(1);
        verify(paymentRepository, times(0)).save(payment);
    }

    @Test
    public void testDeletePayment() {
        PaymentEntity payment = new PaymentEntity();
        payment.setStatus(Status.PENDING);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        paymentService.deleteById(1);
        verify(paymentRepository, times(1)).findById(1);
        verify(paymentRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeletePayment_PaidPayment() {
        PaymentEntity payment = new PaymentEntity();
        payment.setStatus(Status.PAID);
        when(paymentRepository.findById(1)).thenReturn(Optional.of(payment));

        assertThrows(BusinessRuleException.class, () -> paymentService.deleteById(1));
        verify(paymentRepository, times(1)).findById(1);
        verify(paymentRepository, times(0)).deleteById(1);
    }
}
