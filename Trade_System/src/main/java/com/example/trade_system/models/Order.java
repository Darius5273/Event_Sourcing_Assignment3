package com.example.trade_system.models;

import java.math.BigDecimal;

public class Order {
    private String orderId;
    private String userId;
    private String side;
    private BigDecimal price;
    private int quantity;
    private long timestamp;


    public Order(String orderId, String userId, String side, String price, int quantity, long timestamp) {
        this.orderId = orderId;
        this.userId = userId;
        this.side = side;
        this.price = new BigDecimal(price);
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public String toString() {
        return "[" + side + "] Order ID: " + orderId + " | User ID: " + userId + " | Price: " + price.toString() + " | Quantity: " + quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}