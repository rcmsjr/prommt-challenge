package com.server.payment.application.rest.controllers;

import com.server.payment.domain.PaymentService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentServiceTestConfig {

    @Bean
    public PaymentService paymentService() {
        return Mockito.mock(PaymentService.class);
    }
}
