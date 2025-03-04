package com.server.payment.application.rest.requests;

import com.server.payment.domain.Payment;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class CreatePaymentRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid email address")
    private String payerEmail;

    @NotBlank(message = "Currency cannot be blank")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter currency code")
    private String currency;

    @NotNull(message = "Amount cannot be blank")
    @Positive(message = "Amount must be greater than 0 cents")
    private Integer amount;

    public Payment toDomainEntity() {
        Payment payment = new Payment();
        payment.setPayerEmail(this.payerEmail);
        payment.setCurrency(this.currency);
        payment.setAmount(this.amount);
        payment.setStatus(Payment.Status.PENDING);
        return payment;
    }
}
