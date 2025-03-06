export interface IPayment {
  id: number;
  payerEmail: string;
  currency: string;
  amount: number;
  status: 'PENDING' | 'PAID' | 'FAILED';
  createdAt: Date;
  paidAt?: Date;
}

export interface IStoreResponse {
  url: string;
}
