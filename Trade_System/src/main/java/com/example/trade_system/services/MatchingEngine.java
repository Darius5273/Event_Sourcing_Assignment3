package com.example.trade_system.services;

import com.example.trade_system.events.FundsCredited;
import com.example.trade_system.events.FundsDebited;
import com.example.trade_system.events.TradeExecuted;
import com.example.trade_system.models.Order;
import com.example.trade_system.models.OrderBook;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class MatchingEngine {

    private final EventStore eventStore;

    public MatchingEngine(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void match(OrderBook orderBook) throws IOException {
        List<Order> buys = new ArrayList<>();
        List<Order> sells = new ArrayList<>();

        for (Order order : orderBook.getActiveOrders().values()) {
            if (order.getSide().equalsIgnoreCase("buy")) {
                buys.add(order);
            } else {
                sells.add(order);
            }
        }

        buys.sort(Comparator
                .comparing(Order::getPrice).reversed()
                .thenComparingLong(Order::getTimestamp));

        sells.sort(Comparator
                .comparing(Order::getPrice)
                .thenComparingLong(Order::getTimestamp));

        for (Order buy : new ArrayList<>(buys)) {
            for (Order sell : new ArrayList<>(sells)) {
                if (buy.getUserId().equals(sell.getUserId())) continue; 
                if (buy.getPrice().compareTo(sell.getPrice()) < 0) continue;

                int tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());
                BigDecimal tradePrice = sell.getPrice();
                BigDecimal total = tradePrice.multiply(BigDecimal.valueOf(tradedQty));

                if (tradedQty <= 0) continue;

                eventStore.append(new TradeExecuted(
                        UUID.randomUUID().toString(), System.currentTimeMillis(),
                        buy.getOrderId(), sell.getOrderId(),
                        tradePrice.toString(), tradedQty
                ));

                eventStore.append(new FundsDebited(UUID.randomUUID().toString(), System.currentTimeMillis(), buy.getUserId(), total.toString()));
                eventStore.append(new FundsCredited(UUID.randomUUID().toString(), System.currentTimeMillis(), sell.getUserId(), total.toString()));

                buy.setQuantity(buy.getQuantity() - tradedQty);
                sell.setQuantity(sell.getQuantity() - tradedQty);

                if (buy.getQuantity() == 0) break;
            }
        }
    }
}
