package com.example.trade_system.models;

import com.example.trade_system.events.Event;
import com.example.trade_system.events.OrderCancelled;
import com.example.trade_system.events.OrderPlaced;
import com.example.trade_system.events.TradeExecuted;

import java.util.HashMap;
import java.util.Map;

public class OrderBook {

    private final Map<String, Order> activeOrders = new HashMap<>();

    public void apply(Event event) {
        if (event instanceof OrderPlaced e) {
            Order order = new Order(
                    e.getOrderId(), e.getUserId(), e.getSide(),
                    e.getPrice(), e.getQuantity(), e.getTimestamp()
            );
            activeOrders.put(order.getOrderId(), order);
        } else if (event instanceof OrderCancelled e) {
            activeOrders.remove(e.getOrderId());
        } else if (event instanceof TradeExecuted e) {
            adjustOrder(e.getBuyOrderId(), e.getQuantity());
            adjustOrder(e.getSellOrderId(), e.getQuantity());
        }
    }

    private void adjustOrder(String orderId, int quantityExecuted) {
        Order order = activeOrders.get(orderId);
        if (order != null) {
            order.setQuantity(order.getQuantity() - quantityExecuted);
            if (order.getQuantity() <= 0) {
                activeOrders.remove(orderId);
            }
        }
    }

    public Map<String, Order> getActiveOrders() {
        return activeOrders;
    }

    public void printOrderBook() {
        System.out.println("ORDER BOOK:");
        for (Order order : activeOrders.values()) {
            System.out.println(order);
        }
    }
}
