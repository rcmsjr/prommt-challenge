package com.server.payment.infrastructure.database.memory;

import com.server.payment.domain.PaymentEntity;
import com.server.payment.domain.PaymentEntity.Status;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payments")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "payer_email")
    private String payerEmail;
    private String currency;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "paid_date")
    private Date paidDate;

    public PaymentEntity toDomainEntity() {
        PaymentEntity payment = new PaymentEntity();
        payment.setId(this.id);
        payment.setPayerEmail(this.payerEmail);
        payment.setCurrency(this.currency);
        payment.setAmount(this.amount);
        payment.setStatus(this.status);
        payment.setCreatedAt(this.createdDate);
        payment.setPaidAt(this.paidDate);
        return payment;
    }
}
