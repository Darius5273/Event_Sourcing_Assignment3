import axios from 'axios';
import type { PlaceOrderRequest, CreditFundsRequest, CancelOrderRequest } from '../interfaces/types';

const API_BASE = 'http://localhost:8080/api';

export const placeOrder = (data: PlaceOrderRequest) => axios.post(`${API_BASE}/order`, data);
export const creditFunds = (data: CreditFundsRequest) => axios.post(`${API_BASE}/funds`, data);
export const cancelOrder = (data: CancelOrderRequest) => axios.post(`${API_BASE}/cancel`, data);
export const getBalance = (userId: string) => axios.get(`${API_BASE}/balances`, { params: { userId } });
export const getOrders = () => axios.get(`${API_BASE}/orders`);
export const getUserOrders = (userId: string) => axios.get(`${API_BASE}/orders/${userId}`);
export const getUserHistory = (userId: string) => axios.get(`${API_BASE}/history/${userId}`);
