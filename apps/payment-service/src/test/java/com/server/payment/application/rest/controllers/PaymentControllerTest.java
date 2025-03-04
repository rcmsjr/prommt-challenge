package com.server.payment.application.rest.controllers;

import com.server.payment.application.rest.requests.CreatePaymentRequest;
import com.server.payment.application.rest.responses.SavePaymentResponse;
import com.server.payment.domain.PaymentEntity;
import com.server.payment.domain.PaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.context.annotation.Import;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
@Import(PaymentServiceTestConfig.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }

    @Test
    public void testCreatePayment() throws Exception {
        CreatePaymentRequest request = new CreatePaymentRequest();
        SavePaymentResponse response = new SavePaymentResponse();

        when(paymentService.create(any())).thenReturn(new PaymentEntity());

        mockMvc.perform(post("/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"payerEmail\":\"abc@email.com\",\"currency\":\"EUR\",\"amount\":100}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void testFetchPayments() throws Exception {
        when(paymentService.fetch()).thenReturn(Collections.singletonList(new PaymentEntity()));

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testGetPaymentById() throws Exception {
        PaymentEntity returnPayment = new PaymentEntity();
        returnPayment.setId(1L);
        when(paymentService.getById(anyLong())).thenReturn(returnPayment);

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testUpdatePaymentStatusById() throws Exception {
        SavePaymentResponse response = new SavePaymentResponse();

        when(paymentService.updateStatusById(anyLong(), any())).thenReturn(new PaymentEntity());

        mockMvc.perform(patch("/payments/1/pay"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void testDeletePaymentById() throws Exception {
        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isNoContent());
    }
}
