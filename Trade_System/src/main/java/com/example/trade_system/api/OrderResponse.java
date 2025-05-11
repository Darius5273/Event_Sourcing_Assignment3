package com.example.trade_system.api;

public record OrderResponse(String orderId, String userId, String side, String price, int quantity, long timestamp) {

}
