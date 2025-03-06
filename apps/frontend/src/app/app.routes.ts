import { Routes } from '@angular/router';
import { paymentsRoutes } from './modules/payment/payment.routes';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'payments',
    pathMatch: 'full',
  },
  {
    path: 'payments',
    title: 'Payments',
    loadComponent: () =>
      import('./modules/payment/payment.component').then(
        (c) => c.PaymentComponent
      ),
    children: paymentsRoutes,
  },
  { path: '**', redirectTo: 'payments', pathMatch: 'full' },
];
