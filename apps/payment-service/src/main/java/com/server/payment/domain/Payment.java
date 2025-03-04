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
public class Payment {
    private Long id;
    private Date createdAt;
    private Date paidAt;
    private String payerEmail;
    private Status status;
    private String currency;
    private Integer amount;

    public static enum Status {
        PENDING,PAID, FAILED
    }
}
