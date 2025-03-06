import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IPayment } from './payment.interface';
import { PaymentService } from './payment.service';
import dayjs from 'dayjs';

@Component({
  selector: 'app-payment-details',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="payment" class="payment-details">
      <h2>Payment Details</h2>
      <p><strong>Payer Email:</strong> {{ payment.payerEmail }}</p>
      <p>
        <strong>Currency:</strong> {{ payment.currency }}
      </p>
      <p>
        <strong>Amount:</strong> {{ formatAmount(payment.amount) }}
      </p>
      <p><strong>Status:</strong> {{ payment.status }}</p>
      <p><strong>Creation Date:</strong> {{ formatDate(payment.createdAt) }}</p>
      <p *ngIf="payment.paidAt"><strong>Paid at:</strong> {{ formatDate(payment.paidAt) }}</p>
    </div>
  `,
  styles: [
    `
      .payment-details {
        padding: 0.75rem 1rem;
        margin-bottom: 0.5rem;
        border: 1px solid var(--gray-400);
        border-radius: 0.5rem;
        margin-top: 1rem;
      }
    `,
  ],
})
export class PaymentDetailsComponent implements OnChanges {
  @Input() inputPayment: IPayment | null = null;
  payment: IPayment | null = null;

  constructor(private paymentService: PaymentService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['inputPayment'] && this.inputPayment) {
      this.loadPayment(this.inputPayment.id);
    }
  }

  async loadPayment(id: number) {
    try {
      this.payment = await this.paymentService.getById(id);
    } catch (error) {
      console.error('Failed to load payment', error);
    }
  }

  formatAmount(amount: number): string {
    return this.paymentService.formatAmount(amount);
  }

  formatDate(date: Date): string {
    return dayjs(date).format('DD/MM/YYYY HH:mm');
  }
}
