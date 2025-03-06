import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PaymentComponent } from './payment.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [PaymentComponent],
  imports: [
    CommonModule,
    RouterModule.forChild([
      { path: '', component: PaymentComponent }
    ])
  ]
})
export class PaymentModule { }
