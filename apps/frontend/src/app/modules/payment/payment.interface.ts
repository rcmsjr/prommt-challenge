export interface IPayment {
  id: number;
  payerEmail: string;
  currency: string;
  amount: number;
  status: 'PENDING' | 'PAID' | 'FAILED';
}

export interface IStoreResponse {
  url: string;
}
