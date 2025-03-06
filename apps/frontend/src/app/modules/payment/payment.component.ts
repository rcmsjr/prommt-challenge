import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { PaymentService } from './payment.service';
import { IPayment } from './payment.interface';
import { PaymentDetailsComponent } from './payment-details.component';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, RouterOutlet, PaymentDetailsComponent],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  payments: IPayment[] = [];
  selectedPayment: IPayment | null = null;

  constructor(private paymentService: PaymentService) {}

  ngOnInit(): void {
    this.loadPayments();
  }

  async loadPayments() {
    try {
      this.payments = await this.paymentService.fetch();
    } catch (error) {
      console.error('Failed to load payments', error);
    }
  }

  async payNow(payment: IPayment) {
    try {
      await this.paymentService.pay(payment.id);
      this.loadPayments();
    } catch (error) {
      console.error('Failed to load payments', error);
    }
  }

  selectPayment(payment: IPayment) {
    this.selectedPayment = payment;
  }

  formatAmount(amount: number): string {
    return this.paymentService.formatAmount(amount);
  }
}
