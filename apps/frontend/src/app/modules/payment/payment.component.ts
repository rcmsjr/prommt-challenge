import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { PaymentService } from './payment.service';
import { IPayment } from './payment.interface';
import { PaymentDetailsComponent } from './payment-details.component';
import { PaymentFormComponent } from './payment-form.component';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    PaymentDetailsComponent,
    PaymentFormComponent,
  ],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss'],
})
export class PaymentComponent implements OnInit {
  payments: IPayment[] = [];
  selectedPayment: IPayment | null = null;
  isFormVisible: boolean = false;

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
      console.error('Failed to pay payment', error);
    }
  }

  async deletePayment(payment: IPayment) {
    try {
      await this.paymentService.delete(payment.id);
      await this.loadPayments();
      this.selectPayment(this.payments[0])
    } catch (error) {
      console.error('Failed to delete payment', error);
    }
  }

  selectPayment(payment: IPayment) {
    this.selectedPayment = payment;
    this.isFormVisible = false;
  }

  toggleForm() {
    this.isFormVisible = !this.isFormVisible;
  }

  formatAmount(amount: number): string {
    return this.paymentService.formatAmount(amount);
  }
}
