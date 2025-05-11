package com.example.trade_system.models;


import com.example.trade_system.events.*;
import com.example.trade_system.services.UserService;

import java.util.*;

public class UserActivityTracker {
    private final List<String> history = new ArrayList<>();
    private final OrderBook orderBook;
    private final UserService userService;

    public UserActivityTracker(UserService userService, OrderBook orderBook) {
        this.orderBook = orderBook;
        this.userService = userService;
    }

    public void apply(Event event) {
        if (event instanceof TradeExecuted e) {
            String buyer = getUserFromOrderId(e.getBuyOrderId());
            String seller = getUserFromOrderId(e.getSellOrderId());

            history.add(String.format("<strong>Trade:</strong> %s <strong>bought</strong> %d <strong>at price</strong> %s <strong>from</strong> %s",
                    buyer, e.getQuantity(), e.getPrice(), seller));
        } else if (event instanceof FundsCredited e) {
            String user = userService.getUserById(e.getUserId());
            history.add(String.format("<strong>Credited:</strong> %s (<strong>User:</strong> %s)", e.getAmount(), user));
        } else if (event instanceof OrderPlaced e) {
            String user = userService.getUserById(e.getUserId());
            history.add(String.format("<strong>Order Placed:</strong> <strong>order:</strong> %s <strong>by</strong> %s <strong>side:</strong> %s, <strong>quantity:</strong> %d <strong>at price</strong> %s",
                    e.getOrderId(), user, e.getSide().toUpperCase(), e.getQuantity(), e.getPrice()));
        } else if (event instanceof OrderCancelled e) {
            String user = getUserFromOrderId(e.getOrderId());
            history.add(String.format("<strong>Cancelled:</strong> Order %s (<strong>User:</strong> %s)", e.getOrderId(), user));
        }
    }



    private String getUserFromOrderId(String orderId) {
        Order order = orderBook.getActiveOrders().get(orderId);
        return  userService.getUserById(order.getUserId());
    }

    public List<String> getHistory() {
        return history;
    }
}
