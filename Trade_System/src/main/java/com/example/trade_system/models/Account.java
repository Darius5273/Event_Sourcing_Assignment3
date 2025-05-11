package com.example.trade_system.models;

import com.example.trade_system.events.Event;
import com.example.trade_system.events.FundsCredited;
import com.example.trade_system.events.FundsDebited;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Account {
    private final Map<String, BigDecimal> balances = new HashMap<>();

    public void apply(Event event) {
        if (event instanceof FundsCredited e) {
            balances.merge(e.getUserId(), new BigDecimal(e.getAmount()), BigDecimal::add);
        } else if (event instanceof FundsDebited e) {
            balances.merge(e.getUserId(), new BigDecimal("-"+e.getAmount()), BigDecimal::add);
        }
    }

    public BigDecimal getBalance(String userId) {
        return balances.getOrDefault(userId, new BigDecimal("0"));
    }

    public Map<String, BigDecimal> getAllBalances() {
        return balances;
    }
}
