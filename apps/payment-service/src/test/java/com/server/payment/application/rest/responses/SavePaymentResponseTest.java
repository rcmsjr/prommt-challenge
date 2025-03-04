package com.server.payment.application.rest.responses;

import com.server.payment.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavePaymentResponseTest {

    private SavePaymentResponse savePaymentResponse;

    @BeforeEach
    public void setUp() {
        savePaymentResponse = new SavePaymentResponse();
    }

    @Test
    public void testApplyResult() {
        Payment payment = new Payment();
        payment.setId(1L);

        savePaymentResponse.applyResult(payment);

        assertEquals("http://localhost:8080/payments/1", savePaymentResponse.getUrl());
    }

    @Test
    public void testApplyResultWithNullPayment() {
        assertThrows(NullPointerException.class, () -> {
            savePaymentResponse.applyResult(null);
        });
    }

    @Test
    public void testApplyResultWithNegativeId() {
        Payment payment = new Payment();
        payment.setId(-1L);

        savePaymentResponse.applyResult(payment);

        assertEquals("http://localhost:8080/payments/-1", savePaymentResponse.getUrl());
    }
}
