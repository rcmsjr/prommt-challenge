import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { PaymentService } from './payment.service';
import { IPayment } from './payment.interface';

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.scss']
})
export class PaymentComponent implements OnInit {
  payments: IPayment[] = [];

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
}
