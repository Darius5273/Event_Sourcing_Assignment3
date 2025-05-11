package com.example.trade_system.api;

public record PlaceOrderRequest(String userId, String side, String price, int quantity) {

}

