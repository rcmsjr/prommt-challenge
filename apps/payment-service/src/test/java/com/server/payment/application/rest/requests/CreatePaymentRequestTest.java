package com.server.payment.application.rest.requests;

import com.server.payment.domain.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreatePaymentRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidCreatePaymentRequest() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPayerEmail("valid.email@example.com");
        request.setCurrency("USD");
        request.setAmount(100);

        Set<ConstraintViolation<CreatePaymentRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPayerEmail("invalid-email");
        request.setCurrency("USD");
        request.setAmount(100);

        Set<ConstraintViolation<CreatePaymentRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Email must be a valid email address", violations.iterator().next().getMessage());
    }

    @Test
    public void testBlankCurrency() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPayerEmail("valid.email@example.com");
        request.setCurrency("");
        request.setAmount(100);

        Set<ConstraintViolation<CreatePaymentRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Currency cannot be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidCurrency() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPayerEmail("valid.email@example.com");
        request.setCurrency("US");
        request.setAmount(100);

        Set<ConstraintViolation<CreatePaymentRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Currency must be a valid 3-letter currency code", violations.iterator().next().getMessage());
    }

    @Test
    public void testNegativeAmount() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPayerEmail("valid.email@example.com");
        request.setCurrency("USD");
        request.setAmount(-100);

        Set<ConstraintViolation<CreatePaymentRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());
        assertEquals("Amount must be greater than 0 cents", violations.iterator().next().getMessage());
    }

    @Test
    public void testToDomainEntity() {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setPayerEmail("valid.email@example.com");
        request.setCurrency("USD");
        request.setAmount(100);

        Payment payment = request.toDomainEntity();

        assertEquals("valid.email@example.com", payment.getPayerEmail());
        assertEquals("USD", payment.getCurrency());
        assertEquals(100, payment.getAmount());
        assertEquals(Payment.Status.PENDING, payment.getStatus());
    }
}
