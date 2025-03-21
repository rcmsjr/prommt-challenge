import { Injectable } from '@angular/core';
import { axiosInstance } from '../../core/axios.service';
import { IPayment, IStoreResponse } from './payment.interface';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  async fetch(): Promise<IPayment[]> {
    try {
      const response = await axiosInstance.get('/payments');
      return response.data;
    } catch (error) {
      console.error('Error fetching payments:', error);
      throw error;
    }
  }

  async getById(id: number): Promise<IPayment> {
    try {
      const response = await axiosInstance.get(`/payments/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error getting payment:', error);
      throw error;
    }
  }

  async create(paymentData: any): Promise<IStoreResponse> {
    try {
      const response = await axiosInstance.post('/payments', paymentData);
      return response.data;
    } catch (error) {
      console.error('Error creating payment:', error);
      throw error;
    }
  }

  async pay(id: number): Promise<IStoreResponse> {
    try {
      const response = await axiosInstance.patch(`/payments/${id}/pay`);
      return response.data;
    } catch (error) {
      console.error('Error paying payment:', error);
      throw error;
    }
  }

  async delete(id: number): Promise<void> {
    try {
      await axiosInstance.delete(`/payments/${id}`);
    } catch (error) {
      console.error('Error deleting payment:', error);
      throw error;
    }
  }

  formatAmount(amount: number): string {
    return (amount / 100).toFixed(2);
  }
}
