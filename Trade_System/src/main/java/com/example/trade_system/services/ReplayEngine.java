package com.example.trade_system.services;

import com.example.trade_system.models.Account;
import com.example.trade_system.events.Event;
import com.example.trade_system.models.OrderBook;
import com.example.trade_system.models.UserActivityTracker;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReplayEngine {

    private final EventStore eventStore;
    private final Account account;
    private final OrderBook orderBook;
    private final UserActivityTracker userActivityTracker;

    public ReplayEngine(UserService userService, EventStore eventStore) {
        this.eventStore = eventStore;
        this.account = new Account();
        this.orderBook = new OrderBook();
        this.userActivityTracker = new UserActivityTracker(userService, orderBook);
    }

    public void replay() throws IOException {
        this.account.getAllBalances().clear();
        this.orderBook.getActiveOrders().clear();
        this.userActivityTracker.getHistory().clear();
        List<Event> events = eventStore.getAllEvents();
        for (Event e : events) {
            account.apply(e);
            userActivityTracker.apply(e);
            orderBook.apply(e);
        }
    }

    public Account getAccount() {
        return account;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }
    public List<String> getUserHistory(String userId) {
        return userActivityTracker.getHistory().stream()
                .filter(h -> h.contains(userId))
                .collect(Collectors.toList());
    }
}
