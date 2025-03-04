package com.server.payment.application.rest.responses;

import com.server.payment.domain.Payment;

import lombok.NoArgsConstructor;
import lombok.Getter;

@NoArgsConstructor
public class SavePaymentResponse {

    @Getter
    public String url;

    public SavePaymentResponse applyResult(Payment result) {
        this.url = "http://localhost:8080/payments/" + result.getId();
        return this;
    }
}
