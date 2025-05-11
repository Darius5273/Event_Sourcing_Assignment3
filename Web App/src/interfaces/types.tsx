export interface PlaceOrderRequest {
  userId: string;
  side: 'buy' | 'sell';
  price: number;
  quantity: number;
}

export interface CreditFundsRequest {
  userId: string;
  amount: number;
}

export interface CancelOrderRequest {
  orderId: string;
  userId: string;
}

export interface Order {
  orderId: string;
  userId: string;
  side: 'buy' | 'sell';
  price: number;
  quantity: number;
  timestamp: number;
}

export interface User {
  userId: string;
  username: string;
}