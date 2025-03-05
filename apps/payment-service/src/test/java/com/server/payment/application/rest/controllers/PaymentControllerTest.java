package com.server.payment.application.rest.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.context.annotation.Import;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.server.payment.domain.PaymentEntity;
import com.server.payment.domain.PaymentService;
import com.server.payment.domain.exceptions.ResourceNotFoundException;
import com.server.payment.domain.exceptions.BusinessRuleException;
import com.server.payment.infrastructure.http.GlobalExceptionHandler;

@WebMvcTest(controllers = PaymentController.class)
@Import({PaymentServiceTestConfig.class, GlobalExceptionHandler.class})
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    private String generateRandomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testCreatePayment() throws Exception {
        PaymentEntity returnPayment = new PaymentEntity();
        returnPayment.setId(1);
        returnPayment.setPayerEmail("abc@email.com");
        returnPayment.setCurrency("EUR");
        returnPayment.setAmount(10000);
        returnPayment.setStatus(PaymentEntity.Status.PENDING);
        returnPayment.setCreatedAt(new Date());

        when(paymentService.create(any())).thenReturn(returnPayment);

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payerEmail\":\"abc@email.com\",\"currency\":\"EUR\",\"amount\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void testFetchPayments() throws Exception {
        PaymentEntity returnPayment = new PaymentEntity();
        returnPayment.setId(1);
        returnPayment.setPayerEmail("abc@email.com");
        returnPayment.setCurrency("EUR");
        returnPayment.setAmount(10000);
        returnPayment.setStatus(PaymentEntity.Status.PENDING);
        returnPayment.setCreatedAt(new Date());
        when(paymentService.fetch()).thenReturn(Collections.singletonList(returnPayment));

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testGetPaymentById() throws Exception {
        PaymentEntity returnPayment = new PaymentEntity();
        returnPayment.setId(1);
        when(paymentService.getById(anyInt())).thenReturn(returnPayment);

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdatePaymentStatusById() throws Exception {
        when(paymentService.updateStatusById(anyInt(), any())).thenReturn(new PaymentEntity());

        mockMvc.perform(patch("/payments/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void testDeletePaymentById() throws Exception {
        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdatePaymentStatusByIdWithPaidPayment() throws Exception {
        doThrow(new BusinessRuleException("Paid payments cannot be updated")).when(paymentService).updateStatusById(anyInt(), any());

        mockMvc.perform(patch("/payments/1/pay"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePaymentByIdWithPaidPayment() throws Exception {
        doThrow(new BusinessRuleException("Paid payments cannot be updated")).when(paymentService).deleteById(anyInt());

        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPaymentByIdNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Payment not found")).when(paymentService).getById(anyInt());

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePaymentStatusByIdNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Payment not found")).when(paymentService).updateStatusById(anyInt(), any());

        mockMvc.perform(patch("/payments/1/pay"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePaymentByIdNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Payment not found")).when(paymentService).deleteById(anyInt());

        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePaymentWithInvalidData() throws Exception {
        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payerEmail\":\"invalid-email\",\"currency\":\"INVALID\",\"amount\":-100}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreatePaymentWithFuzzyData() throws Exception {
        String fuzzyEmail = generateRandomString(10) + "@test.com";
        String fuzzyCurrency = generateRandomString(3);
        int fuzzyAmount = new Random().nextInt();

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payerEmail\":\"" + fuzzyEmail + "\",\"currency\":\"" + fuzzyCurrency + "\",\"amount\":" + fuzzyAmount + "}"))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testCreatePaymentWithChaos() throws Exception {
        doThrow(new RuntimeException("Chaos Monkey Exception")).when(paymentService).create(any());

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payerEmail\":\"abc@email.com\",\"currency\":\"EUR\",\"amount\":100}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testFetchPaymentsWithChaos() throws Exception {
        doThrow(new RuntimeException("Chaos Monkey Exception")).when(paymentService).fetch();

        mockMvc.perform(get("/payments"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetPaymentByIdWithChaos() throws Exception {
        doThrow(new RuntimeException("Chaos Monkey Exception")).when(paymentService).getById(anyInt());

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testUpdatePaymentStatusByIdWithChaos() throws Exception {
        doThrow(new RuntimeException("Chaos Monkey Exception")).when(paymentService).updateStatusById(anyInt(), any());

        mockMvc.perform(patch("/payments/1/pay"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testDeletePaymentByIdWithChaos() throws Exception {
        doThrow(new RuntimeException("Chaos Monkey Exception")).when(paymentService).deleteById(anyInt());

        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isInternalServerError());
    }
}
