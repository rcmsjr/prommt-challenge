package com.server.payment.application.rest.controllers;

import com.server.payment.application.rest.requests.CreatePaymentRequest;
import com.server.payment.application.rest.responses.SavePaymentResponse;
import com.server.payment.domain.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/payments")
@Validated
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.CREATED)
    public SavePaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest paymentRequest) {
        return new SavePaymentResponse().applyResult(paymentService.create(paymentRequest.toDomainEntity()));
    }

    @GetMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<PaymentEntity> fetchPayments() {
        return paymentService.fetch();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public PaymentEntity getPaymentById(@PathVariable Integer id) {
        return paymentService.getById(id);
    }

    @PatchMapping("/{id}/pay")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public SavePaymentResponse updatePaymentStatusById(@PathVariable Integer id) {
        return new SavePaymentResponse().applyResult(paymentService.updateStatusById(id, PaymentEntity.Status.PAID));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePaymentById(@PathVariable Integer id) {
        paymentService.deleteById(id);
    }
}
