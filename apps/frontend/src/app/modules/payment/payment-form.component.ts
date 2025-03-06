import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PaymentService } from './payment.service';

@Component({
  selector: 'app-payment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="payment-form">
      <h2>Register Payment</h2>
      <form [formGroup]="paymentForm" (ngSubmit)="onSubmit()">
        <div>
          <label for="payerEmail">Payer Email</label><br />
          <input id="payerEmail" formControlName="payerEmail" type="email" required />
        </div>
        <div>
          <label for="currency">Currency</label><br />
          <select id="currency" formControlName="currency" required>
            <option value="EUR">EUR</option>
            <option value="USD">USD</option>
            <option value="GBP">GBP</option>
          </select>
        </div>
        <div>
          <label for="amount">Amount</label><br />
          <input id="amount" formControlName="amount" type="number" required />
        </div>
        <button type="submit" [disabled]="paymentForm.invalid">Submit</button>
      </form>
    </div>
  `,
  styles: [
    `
      .payment-form {
        padding: 0.75rem 1rem;
        margin-bottom: 0.5rem;
        border: 1px solid var(--gray-400);
        border-radius: 0.5rem;
        margin-top: 1rem;
      }

      form {
        display: flex;
        flex-direction: column;
        gap: 1rem;
      }
      label {
        font-weight: bold;
      }
      input, select {
        box-sizing: border-box;
        padding: 0.5rem;
        border: 1px solid var(--gray-400);
        border-radius: 0.25rem;
        width: 100%;
      }
      button {
        padding: 0.5rem 1rem;
        background-color: var(--bright-blue);
        color: #fff;
        border: none;
        border-radius: 0.25rem;
        cursor: pointer;
      }
      button:disabled {
        background-color: var(--gray-400);
      }
    `,
  ],
})
export class PaymentFormComponent {
  @Output() paymentCreated = new EventEmitter<void>();
  paymentForm: FormGroup;

  constructor(private fb: FormBuilder, private paymentService: PaymentService) {
    this.paymentForm = this.fb.group({
      payerEmail: ['', [Validators.required, Validators.email]],
      currency: ['EUR', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
    });
  }

  async onSubmit() {
    if (this.paymentForm.valid) {
      try {
        await this.paymentService.create(this.paymentForm.value);
        alert('Payment registered successfully');
        this.paymentForm.reset();
        this.paymentCreated.emit();
      } catch (error) {
        console.error('Failed to register payment', error);
      }
    }
  }
}
