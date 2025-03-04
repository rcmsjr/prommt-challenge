package com.server.payment.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity {
    private Integer id;
    private String payerEmail;
    private String currency;
    private Integer amount;
    private Status status;
    private Date createdAt;
    private Date paidAt;

    public static enum Status {
        PENDING, PAID, FAILED
    }
}
